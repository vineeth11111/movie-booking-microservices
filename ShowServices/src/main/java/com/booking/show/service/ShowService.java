package com.booking.show.service;

import java.util.List;
import java.util.Set;

import com.booking.show.dto.ShowDTO;
import com.booking.show.entity.Seat;

public interface ShowService {
	ShowDTO createShow(ShowDTO dto);

	List<ShowDTO> getAllShows();

	List<ShowDTO> getShowsByMovieId(Long movieId);

	List<ShowDTO> getShowsByTheaterId(Long theaterId);

	List<Seat> lockSeatsAndSendSeatDetails(Long id, Set<Long> seats);

	Boolean updatingbookedSeat(Long id, Set<Long> seatIds);
}
