package com.booking.ticket.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.ticket.entity.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {
  
}
