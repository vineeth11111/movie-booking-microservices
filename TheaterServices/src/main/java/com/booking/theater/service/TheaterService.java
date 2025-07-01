package com.booking.theater.service;

import java.util.List;

import com.booking.theater.dto.TheaterDto;

public interface TheaterService {
    List<TheaterDto> getAllTheaters();
    TheaterDto getTheaterById(Long id);
    TheaterDto createTheater(TheaterDto dto);
    TheaterDto updateTheater(Long id, TheaterDto dto);
    void deleteTheater(Long id);
}

