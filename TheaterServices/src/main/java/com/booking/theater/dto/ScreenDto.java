package com.booking.theater.dto;

import com.booking.theater.entity.Theater;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScreenDto {

	public ScreenDto(Long id, String name, Integer totalSeats) {
	this.id = id;
	this.name = name;
	this.totalSeats = totalSeats;
	}

	private Long id;

	private String name;
	private Integer totalSeats;

	private Theater theater;
}
