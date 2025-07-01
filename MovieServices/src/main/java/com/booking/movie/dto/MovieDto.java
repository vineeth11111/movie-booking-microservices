package com.booking.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDto {
	
	private Long id;
	private String title;
	private String description;
	private String language;
	private String genre;
	private int duration;
	private String imageUrl;
	private double rating;
	private int voteCount;

	// Getters and Setters
}
