package com.booking.ticket.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.booking.ticket.entity.Booking;
import com.booking.ticket.enums.BookingStatus;
import com.booking.ticket.repo.BookingRepository;
import com.booking.ticket.repo.SeatRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class expirePendingBookingsScheduler {

	private BookingRepository bookingRepository;
	private SeatRepository seatRepository;

	@Scheduled(fixedRate = 60000) // every 1 minute
	public void expirePendingBookings() {
		LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);
		List<Booking> expiredBookings = bookingRepository.findByStatusAndCreatedAtBefore(BookingStatus.PENDING,
				tenMinutesAgo);

		for (Booking booking : expiredBookings) {
			booking.setStatus(BookingStatus.EXPIRED);
			bookingRepository.save(booking);

			seatRepository.saveAll(booking.getSeats());
		}
	}

}
