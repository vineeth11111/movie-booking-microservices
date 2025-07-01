package com.booking.ticket.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.ticket.entity.Booking;
import com.booking.ticket.enums.BookingStatus;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);

	List<Booking> findByStatusAndCreatedAtBefore(BookingStatus pending, LocalDateTime tenMinutesAgo);
}
