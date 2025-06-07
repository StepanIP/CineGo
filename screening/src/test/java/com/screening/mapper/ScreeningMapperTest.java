package com.screening.mapper;

import com.screening.domain.dto.CreatedScreeningDto;
import com.screening.domain.dto.ScreeningAvailableSeats;
import com.screening.domain.dto.ScreeningRequestDto;
import com.screening.domain.model.Cinema;
import com.screening.domain.model.Screening;
import com.screening.domain.model.Seat;
import com.screening.domain.model.SeatStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScreeningMapperTest {

    private final ScreeningMapper screeningMapper = Mappers.getMapper(ScreeningMapper.class);

    @Test
    @DisplayName("Map Screening to CreatedScreeningDto successfully")
    void mapScreeningToCreatedScreeningDtoSuccessfully() {
        // Given
        Screening screening = Screening.builder()
            .id(1L)
            .date(LocalDate.of(2024, 1, 1))
            .time(LocalTime.of(20, 0))
            .filmId(1L)
            .build();

        // When
        CreatedScreeningDto createdDto = screeningMapper.createdEntityToDto(screening);

        // Then
        assertNotNull(createdDto);
        assertEquals(1L, createdDto.id());
        assertEquals(1L, createdDto.filmId());
    }

    @Test
    @DisplayName("Map Screening to ScreeningAvailableSeats successfully")
    void mapScreeningToScreeningAvailableSeatsSuccessfully() {
        // Given
        Seat seat1 = Seat.builder()
            .id(1L)
            .rowsNumber(1)
            .seatInRow(1)
            .status(SeatStatus.AVAILABLE)
            .build();

        Seat seat2 = Seat.builder()
            .id(2L)
            .rowsNumber(1)
            .seatInRow(2)
            .status(SeatStatus.TAKEN)
            .build();

        Screening screening = Screening.builder()
            .id(1L)
            .seats(Arrays.asList(seat1, seat2))
            .build();

        // When
        ScreeningAvailableSeats availableSeats = screeningMapper.screeningToSeatsDto(screening);

        // Then
        assertNotNull(availableSeats);
        List<Seat> seats = availableSeats.seats();
        assertEquals(2, seats.size());
        assertEquals(seat1, seats.get(0));
    }

    @Test
    @DisplayName("Map null values")
    void mapNullValues() {
        assertNull(screeningMapper.dtoToEntity(null));
        assertNull(screeningMapper.createdEntityToDto(null));
        assertNull(screeningMapper.screeningToSeatsDto(null));
    }
} 