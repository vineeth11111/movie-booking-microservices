package com.booking.movie.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.booking.movie.dto.MovieDto;
import com.booking.movie.entity.Movie;
import com.booking.movie.exception.MovieNotFoundException;
import com.booking.movie.repo.MovieRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    private MovieDto toDto(Movie movie) {
        return MovieDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .language(movie.getLanguage())
                .genre(movie.getGenre())
                .duration(movie.getDuration())
                .imageUrl(movie.getImageUrl())
                .rating(movie.getRating())
                .voteCount(movie.getVoteCount())
                .build();
    }

    private Movie toEntity(MovieDto dto) {
        return Movie.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .language(dto.getLanguage())
                .genre(dto.getGenre())
                .duration(dto.getDuration())
                .imageUrl(dto.getImageUrl())
                .rating(dto.getRating())
                .voteCount(dto.getVoteCount())
                .build();
    }

    @Override
    public MovieDto addMovie(MovieDto movieDto) {
        Movie movie = toEntity(movieDto);
        return toDto(movieRepository.save(movie));
    }

    @Override
    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MovieDto getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id " + id));
        return toDto(movie);
    }

    @Override
    public MovieDto updateMovie(Long id, MovieDto movieDto) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id " + id));

        movie.setTitle(movieDto.getTitle());
        movie.setDescription(movieDto.getDescription());
        movie.setLanguage(movieDto.getLanguage());
        movie.setGenre(movieDto.getGenre());
        movie.setDuration(movieDto.getDuration());

        return toDto(movieRepository.save(movie));
    }

    @Override
    public void deleteMovie(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id " + id));
        movieRepository.delete(movie);
    }
}

