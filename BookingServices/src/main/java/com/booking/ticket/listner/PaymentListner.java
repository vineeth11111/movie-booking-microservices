package com.booking.ticket.listner;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.booking.ticket.dto.BookingDto;
import com.booking.ticket.entity.Booking;
import com.booking.ticket.entity.Seat;
import com.booking.ticket.enums.BookingStatus;
import com.booking.ticket.feign.ShowsClient;
import com.booking.ticket.repo.BookingRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class PaymentListner {
	
	private BookingRepository bookingRepo;	
	private ShowsClient showsClient;

	@KafkaListener(topics = "payment_completed_topic", groupId = "payment_completed")
	public void listen(BookingDto bookingDto) {
		log.info("Entered PaymentListner listen method listining to payment_completed_topic "+bookingDto.toString());
	try {
		Optional<Booking> booking = bookingRepo.findById(bookingDto.getId());
		if(booking.isPresent()) {
			Booking updatedBooking = booking.get();
			updatedBooking.setStatus(BookingStatus.CONFIRMED);
			bookingRepo.save(booking.get());
			Long showId = updatedBooking.getShowId();
			Set<Long> seatsBooked = updatedBooking.getSeats().stream().map(Seat::getSeatId).collect(Collectors.toSet());
			showsClient.updatingbookedSeat(showId, seatsBooked);
			//TODO: Email Notification
			System.out.println("Payment Recieved from payment service.. Booking confirmed ... Updated in Booking DB");
		}
	} catch(Exception ex) {
		
	}
	log.info("Completed PaymentListner listen method");
	} 
}
