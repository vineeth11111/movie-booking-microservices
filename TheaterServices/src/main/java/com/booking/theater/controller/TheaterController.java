package com.booking.theater.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.theater.dto.TheaterDto;
import com.booking.theater.service.TheaterService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/theaters")
public class TheaterController {

    private final TheaterService theaterService;

    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    @GetMapping
    public List<TheaterDto> getAll() {
        return theaterService.getAllTheaters();
    }

    @GetMapping("/{id}")
    public TheaterDto getById(@PathVariable Long id) {
        return theaterService.getTheaterById(id);
    }

    @PostMapping
    public ResponseEntity<TheaterDto> create(@RequestBody @Valid TheaterDto dto) {
        return new ResponseEntity<>(theaterService.createTheater(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public TheaterDto update(@PathVariable Long id, @RequestBody @Valid TheaterDto dto) {
        return theaterService.updateTheater(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        theaterService.deleteTheater(id);
        return ResponseEntity.noContent().build();
    }
}

