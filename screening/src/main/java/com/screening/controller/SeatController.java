package com.screening.controller;

import java.util.List;

import com.screening.domain.dto.ScreeningAvailableSeats;
import com.screening.domain.model.Seat;
import com.screening.service.SeatService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/seats")
public class SeatController {

    private final SeatService seatService;

    @GetMapping("/{screeningId}")
    public List<Seat> getSetsByScreeningId(@PathVariable Long screeningId) {
        return seatService.getSeatsByScreeningId(screeningId);
    }

}
