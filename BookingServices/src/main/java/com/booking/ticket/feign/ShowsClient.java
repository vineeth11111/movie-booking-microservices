package com.booking.ticket.feign;

import java.util.List;
import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.booking.ticket.entity.Seat;

@FeignClient(name = "SHOWSERVICE",path = "/shows")
public interface ShowsClient {

	@GetMapping("/show/{id}/seats")
	List<Seat> lockSeatsAndSendSeatDetails(@PathVariable("id") Long id,@RequestParam("seatIds") Set<Long> seatIds);

	 @PostMapping("/show/{id}/seats")
	 public ResponseEntity<String> updatingbookedSeat(@PathVariable("id") Long id, @RequestParam("seatIds") Set<Long> seatIds);
}
