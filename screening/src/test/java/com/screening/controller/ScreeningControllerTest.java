package com.screening.controller;

import com.screening.domain.dto.*;
import com.screening.domain.model.Cinema;
import com.screening.service.ScreeningService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScreeningControllerTest {

    @Mock
    private ScreeningService screeningService;

    @InjectMocks
    private ScreeningController screeningController;

    private ScreeningRequestDto screeningRequestDto;
    private ScreeningResponseDto screeningResponseDto;
    private CreatedScreeningDto createdScreeningDto;
    private Cinema cinema;
    private FilmRequest film;

    @BeforeEach
    void setUp() {
        cinema = Cinema.builder()
            .id(1L)
            .name("Cinema City")
            .build();

        film = FilmRequest.builder()
            .id(1L)
            .title("Test Film")
            .description("Description")
            .releaseDate(LocalDate.of(2023, 12, 1))
            .build();
        screeningRequestDto = new ScreeningRequestDto(
            LocalDate.of(2024, 1, 1),
            LocalTime.of(20, 0),
            cinema.getId()
        );

        screeningResponseDto = new ScreeningResponseDto(
            1L,
            LocalDate.of(2024, 1, 1),
            LocalTime.of(20, 0),
            film,
            new CinemaDto(1L, "Cinema City", "123 Main St", "New York")
        );

        createdScreeningDto = CreatedScreeningDto.builder()
            .id(1L)
            .filmId(1L)
            .date(LocalDate.of(2024, 1, 1))
            .time(LocalTime.of(20, 0))
            .build();
    }

    @Test
    @DisplayName("Get screenings by date successfully")
    void getScreeningsByDate_Successfully() {
        // Given
        LocalDate date = LocalDate.of(2024, 1, 1);
        List<ScreeningResponseDto> screenings = Arrays.asList(screeningResponseDto);
        when(screeningService.getScreeningsByDate(date)).thenReturn(screenings);

        // When
        List<ScreeningResponseDto> result = screeningController.getScreeningsByDate(date);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(screeningResponseDto, result.get(0));
        verify(screeningService).getScreeningsByDate(date);
    }

    @Test
    @DisplayName("Get screenings by date range successfully")
    void getScreeningsByDateRange_Successfully() {
        // Given
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 7);
        List<ScreeningResponseDto> screenings = Arrays.asList(screeningResponseDto);
        when(screeningService.getScreeningsByDateRange(startDate, endDate)).thenReturn(screenings);

        // When
        List<ScreeningResponseDto> result = screeningController.getScreeningsByDateRange(startDate, endDate);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(screeningResponseDto, result.get(0));
        verify(screeningService).getScreeningsByDateRange(startDate, endDate);
    }

    @Test
    @DisplayName("Save screening successfully")
    void saveScreening_Successfully() {
        // Given
        when(screeningService.saveScreening(screeningRequestDto, 1L)).thenReturn(createdScreeningDto);

        // When
        CreatedScreeningDto result = screeningController.saveScreening(screeningRequestDto, 1L);

        // Then
        assertNotNull(result);
        assertEquals(createdScreeningDto, result);
        verify(screeningService).saveScreening(screeningRequestDto, 1L);
    }

    @Test
    @DisplayName("Find screening by ID successfully")
    void findScreeningById_Successfully() {
        // Given
        when(screeningService.getScreeningWithFilm(1L)).thenReturn(screeningResponseDto);

        // When
        ScreeningResponseDto result = screeningController.findScreeningById(1L);

        // Then
        assertNotNull(result);
        assertEquals(screeningResponseDto, result);
        verify(screeningService).getScreeningWithFilm(1L);
    }

    @Test
    @DisplayName("Find available seats successfully")
    void findAvailableSeats_Successfully() {
        // Given
        ScreeningAvailableSeats availableSeats = new ScreeningAvailableSeats(Arrays.asList());
        when(screeningService.findAvailableSeats(1L)).thenReturn(availableSeats);

        // When
        ScreeningAvailableSeats result = screeningController.findAvailableSeats(1L);

        // Then
        assertNotNull(result);
        assertEquals(availableSeats, result);
        verify(screeningService).findAvailableSeats(1L);
    }

    @Test
    @DisplayName("Book seats successfully")
    void bookingSeats_Successfully() {
        // When
        screeningController.bookingSets(1L, 1, 2);

        // Then
        verify(screeningService).bookingSets(1L, 1, 2);
    }

    @Test
    @DisplayName("Get screenings by cinema ID successfully")
    void getScreeningsByCinemaId_Successfully() {
        // Given
        List<ScreeningResponseDto> screenings = Arrays.asList(screeningResponseDto);
        when(screeningService.getScreeningsByCinemaId(1L)).thenReturn(screenings);

        // When
        List<ScreeningResponseDto> result = screeningController.getScreeningsByCinemaId(1L);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(screeningResponseDto, result.get(0));
        verify(screeningService).getScreeningsByCinemaId(1L);
    }

    @Test
    @DisplayName("Get screening discount successfully")
    void getScreeningsByDiscount_Successfully() {
        // Given
        DiscountRequest discountRequest = DiscountRequest.builder()
            .screeningId(1L)
            .seatId(1L)
            .user(new UserRequest())
            .build(
        );
        DiscountResponse discountResponse = new DiscountResponse(/* add necessary parameters */);
        when(screeningService.getDiscount(discountRequest)).thenReturn(discountResponse);

        // When
        DiscountResponse result = screeningController.getScreeningsByDiscount(discountRequest);

        // Then
        assertNotNull(result);
        assertEquals(discountResponse, result);
        verify(screeningService).getDiscount(discountRequest);
    }
} 