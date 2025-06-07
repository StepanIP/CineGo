package com.service.film.service;

import com.service.film.domain.Film;
import com.service.film.domain.Genre;
import com.service.film.dto.CreatedFilmDto;
import com.service.film.dto.FilmRequestDto;
import com.service.film.dto.FilmResponseDto;
import com.service.film.exception.exceptions.AlreadyExistException;
import com.service.film.exception.exceptions.NotFoundException;
import com.service.film.mapper.FilmMapper;
import com.service.film.repository.FilmRepository;
import com.service.film.repository.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private FilmMapper filmMapper;

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private FilmService filmService;

    private FilmRequestDto filmRequestDto;
    private Film film;
    private FilmResponseDto filmResponseDto;
    private CreatedFilmDto createdFilmDto;
    private Genre genre;

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

        film = Film.builder()
            .id(1L)
            .title("Test Film")
            .description("Test Description")
            .durationMinutes(120)
            .releaseDate(LocalDate.now())
            .director("Test Director")
            .rating(4.5)
            .country("Test Country")
            .ageRestriction(16)
            .trailerUrl("http://trailer.url")
            .coverImage("http://cover.image")
            .titleImage("http://title.image")
            .build();

        filmResponseDto = new FilmResponseDto(
            1L,
            "Test Film",
            "Test Description",
            Arrays.asList(),
            120,
            LocalDate.now(),
            "Test Director",
            4.5,
            "Test Country",
            16,
            "http://trailer.url",
            "http://cover.image",
            "http://title.image",
            Arrays.asList()
        );

        createdFilmDto = new CreatedFilmDto(1L, "Test Film");

        genre = new Genre(1L, "Action", null);
    }

    @Test
    @DisplayName("Save film successfully")
    void saveFilm_Successfully() {
        // Given
        when(filmRepository.existsByTitle(filmRequestDto.title())).thenReturn(false);
        when(filmMapper.dtoToEntity(filmRequestDto)).thenReturn(film);
        when(filmRepository.save(film)).thenReturn(film);
        when(filmMapper.createdEntityToDto(film)).thenReturn(createdFilmDto);

        // When
        CreatedFilmDto result = filmService.saveFilm(filmRequestDto);

        // Then
        assertEquals(createdFilmDto, result);
        verify(filmRepository).existsByTitle(filmRequestDto.title());
        verify(filmMapper).dtoToEntity(filmRequestDto);
        verify(filmRepository).save(film);
        verify(filmMapper).createdEntityToDto(film);
    }

    @Test
    @DisplayName("Save film throws AlreadyExistException when title exists")
    void saveFilm_ThrowsAlreadyExistException_WhenTitleExists() {
        // Given
        when(filmRepository.existsByTitle(filmRequestDto.title())).thenReturn(true);

        // When & Then
        assertThrows(AlreadyExistException.class, () -> filmService.saveFilm(filmRequestDto));
        verify(filmRepository).existsByTitle(filmRequestDto.title());
        verifyNoMoreInteractions(filmMapper, filmRepository);
    }

    @Test
    @DisplayName("Find film by ID successfully")
    void findFilmById_Successfully() {
        // Given
        when(filmRepository.findById(1L)).thenReturn(Optional.of(film));
        when(filmMapper.entityToDto(film)).thenReturn(filmResponseDto);

        // When
        FilmResponseDto result = filmService.findFilmById(1L);

        // Then
        assertEquals(filmResponseDto, result);
        verify(filmRepository).findById(1L);
        verify(filmMapper).entityToDto(film);
    }

    @Test
    @DisplayName("Find film by ID throws NotFoundException when film not found")
    void findFilmById_ThrowsNotFoundException_WhenFilmNotFound() {
        // Given
        when(filmRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> filmService.findFilmById(1L));
        verify(filmRepository).findById(1L);
        verifyNoInteractions(filmMapper);
    }

    @Test
    @DisplayName("Find all films successfully")
    void findAllFilms_Successfully() {
        // Given
        List<Film> films = Arrays.asList(film);
        List<FilmResponseDto> expectedDtos = Arrays.asList(filmResponseDto);
        
        when(filmRepository.findAll()).thenReturn(films);
        when(filmMapper.entityToDto(film)).thenReturn(filmResponseDto);

        // When
        List<FilmResponseDto> result = filmService.findAllFilms();

        // Then
        assertEquals(expectedDtos, result);
        verify(filmRepository).findAll();
        verify(filmMapper).entityToDto(film);
    }

    @Test
    @DisplayName("Find films by genre name successfully")
    void findFilmsByGenreName_Successfully() {
        // Given
        List<Film> films = Arrays.asList(film);
        List<FilmResponseDto> expectedDtos = Arrays.asList(filmResponseDto);
        String genreName = "Action";
        
        when(genreRepository.findByName(genreName)).thenReturn(Optional.of(genre));
        when(filmRepository.findByGenresContaining(genre)).thenReturn(films);
        when(filmMapper.entityToDto(film)).thenReturn(filmResponseDto);

        // When
        List<FilmResponseDto> result = filmService.findFilmsByGenreName(genreName);

        // Then
        assertEquals(expectedDtos, result);
        verify(genreRepository).findByName(genreName);
        verify(filmRepository).findByGenresContaining(genre);
        verify(filmMapper).entityToDto(film);
    }

    @Test
    @DisplayName("Find films by genre name throws NotFoundException when genre not found")
    void findFilmsByGenreName_ThrowsNotFoundException_WhenGenreNotFound() {
        // Given
        String genreName = "NonExistentGenre";
        when(genreRepository.findByName(genreName)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> filmService.findFilmsByGenreName(genreName));
        verify(genreRepository).findByName(genreName);
        verifyNoInteractions(filmRepository, filmMapper);
    }
} 