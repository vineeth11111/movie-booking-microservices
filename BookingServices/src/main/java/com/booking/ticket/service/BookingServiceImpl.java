package com.booking.ticket.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.NewTopic;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.booking.ticket.dto.BookingDto;
import com.booking.ticket.dto.BookingRequest;
import com.booking.ticket.dto.BookingResponse;
import com.booking.ticket.entity.Booking;
import com.booking.ticket.entity.Seat;
import com.booking.ticket.enums.BookingStatus;
import com.booking.ticket.enums.SeatStatus;
import com.booking.ticket.exceptions.BookingNotFoundException;
import com.booking.ticket.feign.ShowsClient;
import com.booking.ticket.repo.BookingRepository;
import com.booking.ticket.repo.SeatRepository;

import jakarta.transaction.Transactional;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

	private RedissonClient redissonClient;
	private final BookingRepository bookingRepository;
	private final SeatRepository seatRepository;
	private final ShowsClient showsClient;
	private KafkaTemplate<String, BookingDto> kafkaTemplate;
	@Qualifier("paymentProcess")
	private NewTopic paymentProcess;

	public BookingServiceImpl(BookingRepository bookingRepository, ShowsClient showsClient,
			KafkaTemplate<String, BookingDto> kafkaTemplate, @Qualifier("paymentProcess") NewTopic paymentProcess,
			SeatRepository seatRepository, RedissonClient redissonClient) {
		this.bookingRepository = bookingRepository;
		this.showsClient = showsClient;
		this.kafkaTemplate = kafkaTemplate;
		this.paymentProcess = paymentProcess;
		this.seatRepository = seatRepository;
		this.redissonClient = redissonClient;
	}

	@Override
	@Transactional
	public BookingResponse createBooking(BookingRequest request) {
		log.info("Entered BookingServiceImpl createBooking"+request.toString());
		
		List<RLock> locks = request.getSeatIds().stream().map(id -> redissonClient.getLock("seat-lock-" + id)).toList();

		RLock multiLock = redissonClient.getMultiLock(locks.toArray(new RLock[0]));

		boolean locked = false;

		try {
			locked = multiLock.tryLock(0, 10, TimeUnit.MINUTES); 

			if (locked) {
				List<Seat> selectedSeats = getSeatsFromShowService(request.getShowId(), request.getSeatIds());

				for (Seat seat : selectedSeats) {
					if (!SeatStatus.AVAILABLE.equals(seat.getStatus())) {
						throw new IllegalStateException("Seat " + seat.getId() + " is not available.");
					}
					seat.setStatus(SeatStatus.LOCKED);
				}

				seatRepository.saveAll(selectedSeats);

				BigDecimal totalPrice = selectedSeats.stream().map(Seat::getPrice).reduce(BigDecimal.ZERO,
						BigDecimal::add);

				Booking booking = Booking.builder().userId(request.getUserId()).showId(request.getShowId())
						.seats(selectedSeats).status(BookingStatus.PENDING).totalPrice(totalPrice)
						.createdAt(LocalDateTime.now()).build();

				Booking savedBooking = bookingRepository.save(booking);

				
				initiatePayment(savedBooking);
				log.info("Completed try BookingServiceImpl createBooking saved Booking entity"+savedBooking.toString());
				return buildResponse(savedBooking);
			}

		} catch (Exception e) {
			throw new RuntimeException("Booking failed", e);
		}

		throw new RuntimeException("Unable to lock seats. Please try again.");
	}

	@Override
	public List<BookingResponse> getBookingsByUser(Long userId) {
		return bookingRepository.findByUserId(userId).stream().map(this::buildResponse).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void cancelBooking(Long bookingId) {
		Booking booking = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new BookingNotFoundException("Booking not found"));

		booking.setStatus(BookingStatus.CANCELLED);
		bookingRepository.save(booking);

		// Optional: release seat lock, refund payment, etc.
	}

	private BookingResponse buildResponse(Booking booking) {
		return new BookingResponse(booking.getId(), booking.getUserId(), booking.getShowId(), booking.getStatus(),
				booking.getTotalPrice(), booking.getSeats().stream().map(Seat::getSeatNumber).toList());
	}

	private List<Seat> getSeatsFromShowService(Long showId, Set<Long> seatIds) {
		try {
			List<Seat> seatsFromShowService = showsClient.lockSeatsAndSendSeatDetails(showId, seatIds);
			for (Seat seat : seatsFromShowService) {
				seat.setSeatId(seat.getId());
				seat.setId(null);
				seat.setScreenId(1L);
			}
			return seatsFromShowService;
		} catch (Exception ex) {
			throw new RuntimeException("Something went wrong while accessing data from Show Service");
		}

	}

	private boolean initiatePayment(Booking booking) {
		log.info(" Entered BookingServiceImpl initiatePayment sending message to process_payment_topic and data is "+booking.toString());
		BookingDto bookingDto = BookingDto.builder().id(booking.getId()).totalAmount(booking.getTotalPrice())
				.status("PENDING").build();
		Message<BookingDto> message = MessageBuilder.withPayload(bookingDto)
				.setHeader(KafkaHeaders.TOPIC, paymentProcess.name()).build();
		kafkaTemplate.send(message);
		System.out.println("Message sent to process_payment_topic ....");
		log.info(" Completed BookingServiceImpl initiatePayment sending message to process_payment_topic and data is ");
		return true; // assume success
	}
}
