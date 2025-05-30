package com.screening.domain.dto;

import com.screening.domain.model.Seat;

import java.util.List;

public record ScreeningAvailableSeats(List<Seat> seats) {
}
