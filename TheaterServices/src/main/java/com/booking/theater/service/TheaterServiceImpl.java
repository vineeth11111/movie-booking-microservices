package com.booking.theater.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.theater.dto.ScreenDto;
import com.booking.theater.dto.TheaterDto;
import com.booking.theater.entity.Screen;
import com.booking.theater.entity.Theater;
import com.booking.theater.exceptions.ThreaterNotFoundException;
import com.booking.theater.repo.TheaterRepository;

@Service
public class TheaterServiceImpl implements TheaterService {

	@Autowired
    private final TheaterRepository theaterRepository;

    public TheaterServiceImpl(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

    @Override
    public List<TheaterDto> getAllTheaters() {
        return theaterRepository.findAll().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    @Override
    public TheaterDto getTheaterById(Long id) {
        Theater theater = theaterRepository.findById(id)
            .orElseThrow(() -> new ThreaterNotFoundException("Theater not found with id: " + id));
        return convertToDto(theater);
    }

    @Override
    public TheaterDto createTheater(TheaterDto dto) {
        Theater theater = convertToEntity(dto);
        Theater saved = theaterRepository.save(theater);
        return convertToDto(saved);
    }

    @Override
    public TheaterDto updateTheater(Long id, TheaterDto dto) {
        Theater existing = theaterRepository.findById(id)
            .orElseThrow(() -> new ThreaterNotFoundException("Theater not found with id: " + id));

        existing.setName(dto.getName());
        existing.setCity(dto.getCity());
        existing.setAddress(dto.getAddress());

        existing.getScreens().clear();
        for (ScreenDto screenDto : dto.getScreens()) {
            Screen screen = new Screen();
            screen.setName(screenDto.getName());
            screen.setTotalSeats(screenDto.getTotalSeats());
            screen.setTheater(existing);
            existing.getScreens().add(screen);
        }

        return convertToDto(theaterRepository.save(existing));
    }

    @Override
    public void deleteTheater(Long id) {
        if (!theaterRepository.existsById(id)) {
            throw new ThreaterNotFoundException("Theater not found with id: " + id);
        }
        theaterRepository.deleteById(id);
    }

    // ----------- Mapping Helpers ------------

    private TheaterDto convertToDto(Theater theater) {
        List<ScreenDto> screens = theater.getScreens().stream()
            .map(screen -> new ScreenDto(screen.getId(), screen.getName(), screen.getTotalSeats()))
            .collect(Collectors.toList());

        return new TheaterDto(theater.getId(), theater.getName(), theater.getCity(), theater.getAddress(), screens);
    }

    private Theater convertToEntity(TheaterDto dto) {
        Theater theater = new Theater();
        theater.setName(dto.getName());
        theater.setCity(dto.getCity());
        theater.setAddress(dto.getAddress());

        List<Screen> screens = new ArrayList<>();
        for (ScreenDto screenDto : dto.getScreens()) {
            Screen screen = new Screen();
            screen.setName(screenDto.getName());
            screen.setTotalSeats(screenDto.getTotalSeats());
            screen.setTheater(theater);
            screens.add(screen);
        }
        theater.setScreens(screens);

        return theater;
    }
}

