package com.service.film.controller;

import com.service.film.dto.*;
import com.service.film.service.FilmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilmControllerTest {

    @Mock
    private FilmService filmService;

    @InjectMocks
    private FilmController filmController;

    private FilmRequestDto filmRequestDto;

    private FilmResponseDto filmResponseDto;

    private CreatedFilmDto createdFilmDto;

    private List<FilmResponseDto> filmResponseDtos;

    @BeforeEach
    void setUp() {
        filmRequestDto = new FilmRequestDto(
            "Test Film",
            "Test Description",
            Arrays.asList(1L, 2L),
            120,
            LocalDate.now(),
            "Test Director",
            "Test Country",
            16,
            "http://trailer.url",
            "http://cover.image",
            "http://title.image",
            Arrays.asList(1L, 2L)
        );

        filmResponseDto = new FilmResponseDto(
            1L,
            "Test Film",
            "Test Description",
            Arrays.asList(new GenreResponseDto(1L, "Action"), new GenreResponseDto(2L, "Drama")),
            120,
            LocalDate.now(),
            "Test Director",
            4.5,
            "Test Country",
            16,
            "http://trailer.url",
            "http://cover.image",
            "http://title.image",
            Arrays.asList(new ActorResponseDto(1L, "John", "Doe", "photo1"),
                new ActorResponseDto(2L, "Jane", "Doe", "photo2"))
        );

        createdFilmDto = new CreatedFilmDto(1L, "Test Film");
        filmResponseDtos = Arrays.asList(filmResponseDto);
    }

    @Test
    void saveFilm_ShouldReturnCreatedFilmDto() {
        // Given
        when(filmService.saveFilm(filmRequestDto)).thenReturn(createdFilmDto);

        // When
        CreatedFilmDto result = filmController.saveFilm(filmRequestDto);

        // Then
        assertEquals(createdFilmDto, result);
        verify(filmService).saveFilm(filmRequestDto);
    }

    @Test
    void findAll_ShouldReturnListOfFilms() {
        // Given
        when(filmService.findAllFilms()).thenReturn(filmResponseDtos);

        // When
        List<FilmResponseDto> result = filmController.findAll();

        // Then
        assertEquals(filmResponseDtos, result);
        verify(filmService).findAllFilms();
    }

    @Test
    void findById_ShouldReturnFilm() {
        // Given
        Long filmId = 1L;
        when(filmService.findFilmById(filmId)).thenReturn(filmResponseDto);

        // When
        FilmResponseDto result = filmController.findById(filmId);

        // Then
        assertEquals(filmResponseDto, result);
        verify(filmService).findFilmById(filmId);
    }

    @Test
    void getFilmsByGenre_ShouldReturnFilmsList() {
        // Given
        String genreName = "Action";
        when(filmService.findFilmsByGenreName(genreName)).thenReturn(filmResponseDtos);

        // When
        List<FilmResponseDto> result = filmController.getFilmsByGenre(genreName);

        // Then
        assertEquals(filmResponseDtos, result);
        verify(filmService).findFilmsByGenreName(genreName);
    }

    @Test
    void deleteFilm_ShouldCallServiceDelete() {
        // Given
        Long filmId = 1L;

        // When
        filmController.deleteFilm(filmId);

        // Then
        verify(filmService).deleteFilm(filmId);
    }
} 