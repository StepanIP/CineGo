package com.screening.mapper;

import com.screening.domain.dto.CinemaDto;
import com.screening.domain.model.Cinema;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class CinemaMapperTest {

    private final CinemaMapper cinemaMapper = Mappers.getMapper(CinemaMapper.class);

    @Test
    @DisplayName("Map Cinema to CinemaDto successfully")
    void mapCinemaToCinemaDtoSuccessfully() {
        // Given
        Cinema cinema = Cinema.builder()
            .id(1L)
            .name("Cinema City")
            .address("123 Main St")
            .city("New York")
            .build();

        // When
        CinemaDto cinemaDto = cinemaMapper.toDto(cinema);

        // Then
        assertNotNull(cinemaDto);
        assertEquals(1L, cinemaDto.getId());
        assertEquals("Cinema City", cinemaDto.getName());
        assertEquals("123 Main St", cinemaDto.getAddress());
        assertEquals("New York", cinemaDto.getCity());
    }

    @Test
    @DisplayName("Map CinemaDto to Cinema successfully")
    void mapCinemaDtoToCinemaSuccessfully() {
        // Given
        CinemaDto cinemaDto = CinemaDto.builder()
            .id(1L)
            .name("Cinema City")
            .address("123 Main St")
            .city("New York")
            .build();

        // When
        Cinema cinema = cinemaMapper.toEntity(cinemaDto);

        // Then
        assertNotNull(cinema);
        assertEquals(1L, cinema.getId());
        assertEquals("Cinema City", cinema.getName());
        assertEquals("123 Main St", cinema.getAddress());
        assertEquals("New York", cinema.getCity());
    }

    @Test
    @DisplayName("Map null Cinema to null DTO")
    void mapNullCinemaToNullDto() {
        assertNull(cinemaMapper.toDto(null));
        assertNull(cinemaMapper.toEntity(null));
    }
} 