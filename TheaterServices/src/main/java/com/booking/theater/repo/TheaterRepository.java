package com.booking.theater.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.theater.entity.Theater;

public interface TheaterRepository extends JpaRepository<Theater, Long>{

}
