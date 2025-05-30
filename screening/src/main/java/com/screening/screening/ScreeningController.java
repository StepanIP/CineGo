package com.screening.screening;

import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.screening.screening.ScreeningController.Routes.*;

import com.screening.screening.dto.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/screenings")
public class ScreeningController {

    private final ScreeningService service;

    @GetMapping
    public List<ScreeningResponseDto> getScreeningsByDate(
        @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return service.getScreeningsByDate(date);
    }

    @GetMapping("/range")
    public List<ScreeningResponseDto> getScreeningsByDateRange(
        @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return service.getScreeningsByDateRange(startDate, endDate);
    }

    @PostMapping("/{filmId}")
    @ResponseStatus(code = org.springframework.http.HttpStatus.CREATED)
    public CreatedScreeningDto saveScreening(@RequestBody ScreeningRequestDto screeningDto, @PathVariable Long filmId) {
        return service.saveScreening(screeningDto, filmId);
    }

    @GetMapping("/{screeningId}")
    public ScreeningResponseDto findScreeningById(@PathVariable Long screeningId) {
        return service.getScreeningWithFilm(screeningId);
    }

    @GetMapping("/seats/{id}")
    public ScreeningAvailableSeats findAvailableSeats(@PathVariable Long id) {
        return service.findAvailableSeats(id);
    }

    @PutMapping("/booking/seats/{screeningId}/{rowNumber}/{seatsNumber}")
    public void bookingSets(@PathVariable Long screeningId, @PathVariable int rowNumber, @PathVariable int seatsNumber) {
        service.bookingSets(screeningId, rowNumber, seatsNumber);
    }

    @GetMapping("/cinemaId")
    public List<ScreeningResponseDto> getScreeningsByCinemaId(@RequestParam("cinemaId") Long cinemaId) {
        return service.getScreeningsByCinemaId(cinemaId);
    }
}
