package com.booking.show.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.booking.show.dto.ShowDTO;
import com.booking.show.entity.Seat;
import com.booking.show.entity.Show;
import com.booking.show.enums.SeatStatus;
import com.booking.show.repo.SeatRepository;
import com.booking.show.repo.ShowRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ShowServiceImpl implements ShowService {

    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;
    

    @Override
    public ShowDTO createShow(ShowDTO dto) {
        Show show = mapToEntity(dto);
        Show savedShow = showRepository.save(show);
        return mapToDTO(savedShow);
    }

    @Override
    public List<ShowDTO> getAllShows() {
        return showRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowDTO> getShowsByMovieId(Long movieId) {
        return showRepository.findByMovieId(movieId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowDTO> getShowsByTheaterId(Long theaterId) {
        return showRepository.findByTheaterId(theaterId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ======= Manual Mapper Methods =======

    private ShowDTO mapToDTO(Show show) {
        ShowDTO dto = new ShowDTO();
        dto.setId(show.getId());
        dto.setMovieId(show.getMovieId());
        dto.setTheaterId(show.getTheaterId());
        dto.setScreenId(show.getScreenId());
        dto.setStartTime(show.getStartTime());
        return dto;
    }

    private Show mapToEntity(ShowDTO dto) {
        Show show = new Show();
        show.setId(dto.getId()); // Optional: only if needed for updates
        show.setMovieId(dto.getMovieId());
        show.setTheaterId(dto.getTheaterId());
        show.setScreenId(dto.getScreenId());
        show.setStartTime(dto.getStartTime());
        return show;
    }

	@Override
	public List<Seat> lockSeatsAndSendSeatDetails(Long showId, Set<Long> seats) {
	/*	Optional<Show> showById = showRepository.findById(showId);
		if(showById.isPresent()) {
			List<Seat> seatsSelected = showById.get().getSeats().stream()
			.filter(seat -> seats.contains(seat.getId()))
			.collect(Collectors.toList());
			
			return seatsSelected;
		}
		else {
			throw new RuntimeException("Seats are not available for provided show.. ");
		} */
		List<Seat> listOfSeats = showRepository.getDetailsByShowIdAndSeatIds(showId, seats);
		return  listOfSeats;
	}

	@Override
	public Boolean updatingbookedSeat(Long showId, Set<Long> seatIds) {
		 List<Seat> seats = showRepository.getDetailsByShowIdAndSeatIds(showId, seatIds);
		 for(Seat seat: seats) {
			 seat.setIsbooked(true);
			 seat.setStatus(SeatStatus.BOOKED);
		 }
		 seatRepository.saveAll(seats);
		return true;
	}
}
