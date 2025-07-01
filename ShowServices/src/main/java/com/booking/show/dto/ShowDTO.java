package com.booking.show.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ShowDTO {

	private Long id;
	private Long movieId;
	private Long theaterId;
	private Long screenId;
	private LocalDateTime startTime;

}
