package com.booking.movie.service;


import java.util.List;

import com.booking.movie.dto.MovieDto;

public interface MovieService {
    MovieDto addMovie(MovieDto movieDto);
    List<MovieDto> getAllMovies();
    MovieDto getMovieById(Long id);
    MovieDto updateMovie(Long id, MovieDto movieDto);
    void deleteMovie(Long id);
}
