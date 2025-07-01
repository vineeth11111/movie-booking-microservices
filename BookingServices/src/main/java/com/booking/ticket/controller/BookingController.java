package com.booking.ticket.controller;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.ticket.dto.BookingRequest;
import com.booking.ticket.dto.BookingResponse;
import com.booking.ticket.feign.ShowsClient;
import com.booking.ticket.service.BookingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BookingService bookingService;
    
    @PostMapping
    public BookingResponse createBooking(@RequestBody BookingRequest request) {
    	log.info("Entered BookingController createBooking ");
    	BookingResponse booking = bookingService.createBooking(request);
    	log.info("Completed BookingController createBooking ");
        return booking;
    }

    @GetMapping("/user/{userId}")
    public List<BookingResponse> getUserBookings(@PathVariable Long userId) {
        return bookingService.getBookingsByUser(userId);
    }

    @PatchMapping("/{id}/cancel")
    public void cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
    }
    
   
}

