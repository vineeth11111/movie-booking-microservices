package com.booking.show.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.show.entity.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {

}