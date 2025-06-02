package com.screening.cotroller;

import com.screening.domain.dto.CreatedScreeningDto;
import com.screening.domain.dto.DiscountRequest;
import com.screening.domain.dto.DiscountResponse;
import com.screening.domain.dto.ScreeningAvailableSeats;
import com.screening.domain.dto.ScreeningRequestDto;
import com.screening.domain.dto.ScreeningResponseDto;
import com.screening.domain.dto.UserRequest;
import com.screening.service.ScreeningService;
import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/discount")
    public DiscountResponse getScreeningsByDiscount(
        @RequestBody DiscountRequest discountRequest
    ) {
        return service.getDiscount(discountRequest);
    }
}
