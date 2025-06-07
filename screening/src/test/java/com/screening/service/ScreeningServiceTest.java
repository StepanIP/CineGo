package com.screening.service;

import com.screening.client.FilmClient;
import com.screening.domain.dto.*;
import com.screening.domain.model.Cinema;
import com.screening.domain.model.Screening;
import com.screening.domain.model.Seat;
import com.screening.domain.model.SeatStatus;
import com.screening.exceptions.NotFoundException;
import com.screening.facade.SeatFacade;
import com.screening.mapper.CinemaMapper;
import com.screening.mapper.ScreeningMapper;
import com.screening.repository.ScreeningRepository;
import com.screening.validator.ScreeningValidate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScreeningServiceTest {

    @Mock
    private ScreeningRepository screeningRepository;

    @Mock
    private ScreeningMapper screeningMapper;

    @Mock
    private ScreeningValidate screeningValidate;

    @Mock
    private FilmClient filmClient;

    @Mock
    private SeatFacade seatFacade;

    @Mock
    private CinemaMapper cinemaMapper;

    @InjectMocks
    private ScreeningService screeningService;

    private Screening screening;
    private ScreeningRequestDto screeningRequestDto;
    private FilmRequest filmRequest;
    private Cinema cinema;
    private CinemaDto cinemaDto;

    @BeforeEach
    void setUp() {
        cinema = Cinema.builder()
            .id(1L)
            .name("Cinema City")
            .build();

        cinemaDto = CinemaDto.builder()
            .id(1L)
            .name("Cinema City")
            .build();

        screening = Screening.builder()
            .id(1L)
            .date(LocalDate.of(2024, 1, 1))
            .time(LocalTime.of(20, 0))
            .filmId(1L)
            .cinema(cinema)
            .build();

        screeningRequestDto = new ScreeningRequestDto(
            LocalDate.of(2024, 1, 1),
            LocalTime.of(20, 0),
            cinema.getId()
        );

        filmRequest = FilmRequest.builder()
            .id(1L)
            .title("Test Film")
            .description("Description")
            .build();
    }

    @Test
    @DisplayName("Save screening successfully")
    void saveScreening_Successfully() {
        // Given
        when(filmClient.findFilmById(1L)).thenReturn(filmRequest);
        when(screeningMapper.dtoToEntity(screeningRequestDto)).thenReturn(screening);
        when(screeningRepository.save(any(Screening.class))).thenReturn(screening);
        when(screeningMapper.createdEntityToDto(screening)).thenReturn(new CreatedScreeningDto(1L, LocalDate.now(), LocalTime.now(), 1L, 1L));

        // When
        CreatedScreeningDto result = screeningService.saveScreening(screeningRequestDto, 1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(1L, result.filmId());
        verify(screeningValidate).dataValidation(screeningRequestDto, filmRequest);
        verify(screeningRepository).save(any(Screening.class));
    }

    @Test
    @DisplayName("Get screening with film successfully")
    void getScreeningWithFilm_Successfully() {
        // Given
        when(screeningRepository.findById(1L)).thenReturn(Optional.of(screening));
        when(filmClient.findById(1L)).thenReturn(ResponseEntity.ok(filmRequest));
        when(cinemaMapper.toDto(cinema)).thenReturn(cinemaDto);

        // When
        ScreeningResponseDto result = screeningService.getScreeningWithFilm(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(filmRequest, result.film());
        assertEquals(cinemaDto, result.cinema());
        verify(screeningRepository).findById(1L);
        verify(filmClient).findById(1L);
    }

    @Test
    @DisplayName("Get screenings by date successfully")
    void getScreeningsByDate_Successfully() {
        // Given
        LocalDate date = LocalDate.of(2024, 1, 1);
        List<Screening> screenings = Arrays.asList(screening);
        when(screeningRepository.findScreeningsByDate(date)).thenReturn(screenings);
        when(filmClient.findById(1L)).thenReturn(ResponseEntity.ok(filmRequest));
        when(cinemaMapper.toDto(cinema)).thenReturn(cinemaDto);
        when(screeningRepository.findById(1L)).thenReturn(Optional.of(screening));


        // When
        List<ScreeningResponseDto> result = screeningService.getScreeningsByDate(date);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).id());
        verify(screeningValidate).checkCorrectData(date);
        verify(screeningRepository).findScreeningsByDate(date);
    }

    @Test
    @DisplayName("Get screenings by date range successfully")
    void getScreeningsByDateRange_Successfully() {
        // Given
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 7);
        List<Screening> screenings = Arrays.asList(screening);
        when(screeningRepository.findScreeningsByDateBetween(startDate, endDate)).thenReturn(screenings);
        when(filmClient.findById(1L)).thenReturn(ResponseEntity.ok(filmRequest));
        when(cinemaMapper.toDto(cinema)).thenReturn(cinemaDto);
        when(screeningRepository.findById(1L)).thenReturn(Optional.of(screening));

        // When
        List<ScreeningResponseDto> result = screeningService.getScreeningsByDateRange(startDate, endDate);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).id());
        verify(screeningValidate).checkCorrectDateRange(startDate, endDate);
        verify(screeningRepository).findScreeningsByDateBetween(startDate, endDate);
    }

    @Test
    @DisplayName("Find screening by ID successfully")
    void findById_Successfully() {
        // Given
        when(screeningRepository.findById(1L)).thenReturn(Optional.of(screening));

        // When
        Screening result = screeningService.findById(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(screeningRepository).findById(1L);
    }

    @Test
    @DisplayName("Find screening by ID throws NotFoundException when not found")
    void findById_ThrowsNotFoundException_WhenNotFound() {
        // Given
        when(screeningRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> screeningService.findById(1L));
        verify(screeningRepository).findById(1L);
    }

    @Test
    @DisplayName("Find available seats successfully")
    void findAvailableSeats_Successfully() {
        // Given
        Seat availableSeat = Seat.builder()
            .id(1L)
            .rowsNumber(1)
            .seatInRow(1)
            .status(SeatStatus.AVAILABLE)
            .build();
        screening.setSeats(Arrays.asList(availableSeat));

        when(screeningRepository.findById(1L)).thenReturn(Optional.of(screening));
        when(screeningMapper.screeningToSeatsDto(screening)).thenReturn(new ScreeningAvailableSeats(Arrays.asList(availableSeat)));

        // When
        ScreeningAvailableSeats result = screeningService.findAvailableSeats(1L);

        // Then
        assertNotNull(result);
        assertEquals(1, result.seats().size());
        assertEquals(availableSeat, result.seats().get(0));
        verify(screeningRepository).findById(1L);
        verify(screeningMapper).screeningToSeatsDto(screening);
    }

    @Test
    @DisplayName("Get screenings by cinema ID successfully")
    void getScreeningsByCinemaId_Successfully() {
        // Given
        List<Screening> screenings = Arrays.asList(screening);
        when(screeningRepository.findByCinemaId(1L)).thenReturn(screenings);
        when(filmClient.findById(1L)).thenReturn(ResponseEntity.ok(filmRequest));
        when(cinemaMapper.toDto(cinema)).thenReturn(cinemaDto);
        when(screeningRepository.findById(1L)).thenReturn(Optional.of(screening));

        // When
        List<ScreeningResponseDto> result = screeningService.getScreeningsByCinemaId(1L);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).id());
        verify(screeningRepository).findByCinemaId(1L);
    }

    @Test
    @DisplayName("Create seats successfully")
    void createSeats_Successfully() {
        // Given
        Seat seat = Seat.builder()
            .rowsNumber(1)
            .seatInRow(1)
            .status(SeatStatus.AVAILABLE)
            .build();
        when(seatFacade.createSeat(anyInt(), anyInt(), any(SeatStatus.class))).thenReturn(seat);

        // When
        List<Seat> result = screeningService.createSeats();

        // Then
        assertNotNull(result);
        assertEquals(100, result.size()); // 10x10 grid
        verify(seatFacade, times(100)).createSeat(anyInt(), anyInt(), eq(SeatStatus.AVAILABLE));
    }
} 