package com.booking.show.controller;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.booking.show.dto.ShowDTO;
import com.booking.show.entity.Seat;
import com.booking.show.service.ShowService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/shows")
@AllArgsConstructor
public class ShowController {

    private final ShowService showService;

    @PostMapping
    public ResponseEntity<ShowDTO> createShow(@RequestBody @Valid ShowDTO showDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(showService.createShow(showDTO));
    }

    @GetMapping
    public ResponseEntity<List<ShowDTO>> getAllShows() {
        return ResponseEntity.ok(showService.getAllShows());
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ShowDTO>> getShowsByMovie(@PathVariable Long movieId) {
        return ResponseEntity.ok(showService.getShowsByMovieId(movieId));
    }

    @GetMapping("/theater/{theaterId}")
    public ResponseEntity<List<ShowDTO>> getShowsByTheater(@PathVariable Long theaterId) {
        return ResponseEntity.ok(showService.getShowsByTheaterId(theaterId));
    }
    
    @GetMapping("/show/{id}/seats")
    public ResponseEntity<List<Seat>> lockSeatsAndSendSeatDetails(@PathVariable("id") Long id, @RequestParam("seatIds") Set<Long> seatIds) {
    	List<Seat> seatsSelected = showService.lockSeatsAndSendSeatDetails(id,seatIds);
		return new ResponseEntity<List<Seat>>(seatsSelected, HttpStatus.OK);
    }
    
    @PostMapping("/show/{id}/seats")
    public ResponseEntity<String> updatingbookedSeat(@PathVariable("id") Long id, @RequestParam("seatIds") Set<Long> seatIds) {
    	showService.updatingbookedSeat(id,seatIds);
		return new ResponseEntity<String>("Seats Marked as Booked in Show Service DB", HttpStatus.OK);
    }
}
