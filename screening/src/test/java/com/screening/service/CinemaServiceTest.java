package com.screening.service;

import com.screening.domain.dto.CinemaDto;
import com.screening.domain.model.Cinema;
import com.screening.mapper.CinemaMapper;
import com.screening.repository.CinemaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CinemaServiceTest {

    @Mock
    private CinemaRepository cinemaRepository;

    @Mock
    private CinemaMapper cinemaMapper;

    @InjectMocks
    private CinemaService cinemaService;

    private Cinema cinema;
    private CinemaDto cinemaDto;

    @BeforeEach
    void setUp() {
        cinema = Cinema.builder()
            .id(1L)
            .name("Cinema City")
            .address("123 Main St")
            .city("New York")
            .build();

        cinemaDto = CinemaDto.builder()
            .id(1L)
            .name("Cinema City")
            .address("123 Main St")
            .city("New York")
            .build();
    }

    @Test
    @DisplayName("Find all cinemas successfully")
    void findAll_Successfully() {
        // Given
        List<Cinema> cinemas = Arrays.asList(cinema);
        when(cinemaRepository.findAll()).thenReturn(cinemas);
        when(cinemaMapper.toDto(cinema)).thenReturn(cinemaDto);

        // When
        List<CinemaDto> result = cinemaService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(cinemaDto, result.get(0));
        verify(cinemaRepository).findAll();
        verify(cinemaMapper).toDto(cinema);
    }

    @Test
    @DisplayName("Find cinema by ID successfully")
    void findById_Successfully() {
        // Given
        when(cinemaRepository.findById(1L)).thenReturn(Optional.of(cinema));
        when(cinemaMapper.toDto(cinema)).thenReturn(cinemaDto);

        // When
        CinemaDto result = cinemaService.findById(1L);

        // Then
        assertNotNull(result);
        assertEquals(cinemaDto, result);
        verify(cinemaRepository).findById(1L);
        verify(cinemaMapper).toDto(cinema);
    }

    @Test
    @DisplayName("Find cinema by ID throws exception when not found")
    void findById_ThrowsException_WhenNotFound() {
        // Given
        when(cinemaRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> cinemaService.findById(1L));
        verify(cinemaRepository).findById(1L);
        verifyNoInteractions(cinemaMapper);
    }

    @Test
    @DisplayName("Create cinema successfully")
    void create_Successfully() {
        // Given
        when(cinemaMapper.toEntity(cinemaDto)).thenReturn(cinema);
        when(cinemaRepository.save(cinema)).thenReturn(cinema);
        when(cinemaMapper.toDto(cinema)).thenReturn(cinemaDto);

        // When
        CinemaDto result = cinemaService.create(cinemaDto);

        // Then
        assertNotNull(result);
        assertEquals(cinemaDto, result);
        verify(cinemaMapper).toEntity(cinemaDto);
        verify(cinemaRepository).save(cinema);
        verify(cinemaMapper).toDto(cinema);
    }

    @Test
    @DisplayName("Update cinema successfully")
    void update_Successfully() {
        // Given
        CinemaDto updateDto = CinemaDto.builder()
            .name("Updated Cinema")
            .address("456 New St")
            .city("Los Angeles")
            .build();

        when(cinemaRepository.findById(1L)).thenReturn(Optional.of(cinema));
        when(cinemaRepository.save(any(Cinema.class))).thenReturn(cinema);
        when(cinemaMapper.toDto(any(Cinema.class))).thenReturn(updateDto);

        // When
        CinemaDto result = cinemaService.update(1L, updateDto);

        // Then
        assertNotNull(result);
        assertEquals("Updated Cinema", result.getName());
        assertEquals("456 New St", result.getAddress());
        assertEquals("Los Angeles", result.getCity());
        verify(cinemaRepository).findById(1L);
        verify(cinemaRepository).save(cinema);
        verify(cinemaMapper).toDto(cinema);
    }

    @Test
    @DisplayName("Update cinema throws exception when not found")
    void update_ThrowsException_WhenNotFound() {
        // Given
        when(cinemaRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> cinemaService.update(1L, cinemaDto));
        verify(cinemaRepository).findById(1L);
        verifyNoMoreInteractions(cinemaRepository);
        verifyNoInteractions(cinemaMapper);
    }

    @Test
    @DisplayName("Delete cinema successfully")
    void delete_Successfully() {
        // When
        cinemaService.delete(1L);

        // Then
        verify(cinemaRepository).deleteById(1L);
    }
} 