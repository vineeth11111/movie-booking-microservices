package com.booking.ticket.service;

import java.util.List;

import com.booking.ticket.dto.BookingRequest;
import com.booking.ticket.dto.BookingResponse;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request);
    List<BookingResponse> getBookingsByUser(Long userId);
    void cancelBooking(Long bookingId);
}
