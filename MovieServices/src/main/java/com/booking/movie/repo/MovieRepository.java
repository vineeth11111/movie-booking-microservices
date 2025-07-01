package com.booking.movie.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.movie.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}

