package com.booking.show.runner;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.booking.show.entity.Seat;
import com.booking.show.entity.Show;
import com.booking.show.repo.ShowRepository;

import lombok.AllArgsConstructor;

//@Component
@AllArgsConstructor
public class SlotDataRunner implements CommandLineRunner{
	
	
	private ShowRepository showRepo;

	@Override
	public void run(String... args) throws Exception {
		Show show = new Show();
		show.setMovieId(1L);
		show.setTheaterId(1L);
		show.setScreenId(1L);
		show.setStartTime(LocalDateTime.now());
		
		List<Seat> seats = new ArrayList<>();
		
		for(int count = 1; count <= 20; count++) {
			Seat seat = new Seat();
			seat.setSeatNumber("A"+count);
			seat.setPrice(new BigDecimal(150));
			seats.add(seat);
		}
		show.setSeats(seats);
		showRepo.save(show);
		System.out.println("Data saved successfully in the DB.....");
	}

}
