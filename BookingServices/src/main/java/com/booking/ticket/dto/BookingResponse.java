package com.booking.ticket.dto;

import java.math.BigDecimal;
import java.util.List;

import com.booking.ticket.enums.BookingStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private Long bookingId;
    private Long userId;
    private Long showId;
    private BookingStatus status;
    private BigDecimal totalPrice;
    private List<String> seatNumbers;
}
