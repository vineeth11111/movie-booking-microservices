package com.booking.show.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.booking.show.entity.Seat;
import com.booking.show.entity.Show;

public interface ShowRepository extends JpaRepository<Show, Long> {
    List<Show> findByMovieId(Long movieId);
    List<Show> findByTheaterId(Long theaterId);
    
    @Query("SELECT s FROM Show sh JOIN sh.seats s WHERE sh.id = :showId AND s.id IN :seatIds")
    List<Seat> getDetailsByShowIdAndSeatIds(@Param("showId") Long showId, @Param("seatIds") Set<Long> seatIds);

}


