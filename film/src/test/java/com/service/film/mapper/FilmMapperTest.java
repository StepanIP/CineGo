package com.service.film.mapper;

import com.service.film.domain.Actor;
import com.service.film.domain.Film;
import com.service.film.domain.Genre;
import com.service.film.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilmMapperTest {

    private final FilmMapper filmMapper = Mappers.getMapper(FilmMapper.class);

    @Test
    @DisplayName("Map FilmRequestDto to Film successfully")
    void mapFilmRequestDtoToFilmSuccessfully() {
        // Given
        FilmRequestDto requestDto = new FilmRequestDto(
                "Test Film",
                "Test Description",
                Arrays.asList(1L, 2L),
                120,
                LocalDate.of(2024, 1, 1),
                "Test Director",
                "Test Country",
                16,
                "http://trailer.url",
                "http://cover.image",
                "http://title.image",
                Arrays.asList(1L, 2L)
        );

        // When
        Film film = filmMapper.dtoToEntity(requestDto);

        // Then
        assertNotNull(film);
        assertEquals("Test Film", film.getTitle());
        assertEquals("Test Description", film.getDescription());
        assertEquals(120, film.getDurationMinutes());
        assertEquals(LocalDate.of(2024, 1, 1), film.getReleaseDate());
        assertEquals("Test Director", film.getDirector());
        assertEquals(0.0, film.getRating()); // Default rating
        assertEquals("Test Country", film.getCountry());
        assertEquals(16, film.getAgeRestriction());
        assertEquals("http://trailer.url", film.getTrailerUrl());
        assertEquals("http://cover.image", film.getCoverImage());
        assertEquals("http://title.image", film.getTitleImage());
    }

    @Test
    @DisplayName("Map Film to CreatedFilmDto successfully")
    void mapFilmToCreatedFilmDtoSuccessfully() {
        // Given
        Film film = Film.builder()
                .id(1L)
                .title("Test Film")
                .build();

        // When
        CreatedFilmDto createdFilmDto = filmMapper.createdEntityToDto(film);

        // Then
        assertNotNull(createdFilmDto);
        assertEquals(1L, createdFilmDto.id());
        assertEquals("Test Film", createdFilmDto.title());
    }

    @Test
    @DisplayName("Map null Film to null DTO")
    void mapNullFilmToNullDto() {
        assertNull(filmMapper.entityToDto(null));
        assertNull(filmMapper.createdEntityToDto(null));
        assertNull(filmMapper.dtoToEntity(null));
    }
} 