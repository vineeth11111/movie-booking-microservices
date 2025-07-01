package com.booking.payment.feign;


import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "BOOKINGSERVICES")
public interface BookingClient {

   // @PatchMapping("/bookings/{id}/confirm")
    void confirmBooking(@PathVariable Long id);

   // @PatchMapping("/bookings/{id}/cancel")
    void cancelBooking(@PathVariable Long id);
}

