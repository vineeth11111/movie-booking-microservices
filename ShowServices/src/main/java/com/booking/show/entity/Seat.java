package com.booking.show.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.booking.show.enums.SeatStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "seats")
public class Seat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String seatNumber;
	private BigDecimal price;
	private boolean isbooked;
	private LocalDateTime lockedDateTime;
	private SeatStatus status ;
}
