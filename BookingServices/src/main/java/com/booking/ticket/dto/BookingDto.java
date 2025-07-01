package com.booking.ticket.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDto {

	private Long id;
	private BigDecimal totalAmount;
	private String status = "PENDING";
}
