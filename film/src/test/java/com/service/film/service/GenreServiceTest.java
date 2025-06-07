package com.service.film.service;

import com.service.film.domain.Film;
import com.service.film.domain.Genre;
import com.service.film.dto.FilmResponseDto;
import com.service.film.dto.GenreRequestDto;
import com.service.film.dto.GenreResponseDto;
import com.service.film.mapper.FilmMapper;
import com.service.film.mapper.GenreMapper;
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
class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private GenreMapper genreMapper;

    @Mock
    private FilmMapper filmMapper;

    @InjectMocks
    private GenreService genreService;

    private GenreRequestDto genreRequestDto;
    private Genre genre;
    private GenreResponseDto genreResponseDto;
    private Film film;
    private FilmResponseDto filmResponseDto;

    @BeforeEach
    void setUp() {
        genreRequestDto = new GenreRequestDto("Action");
        
        genre = new Genre(1L, "Action", null);
        
        genreResponseDto = new GenreResponseDto(1L, "Action");
        
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
            null,
            null,
            null,
            Arrays.asList()
        );
    }

    @Test
    @DisplayName("Create genre successfully")
    void createGenre_Successfully() {
        // Given
        when(genreMapper.dtoToEntity(genreRequestDto)).thenReturn(genre);
        when(genreRepository.save(genre)).thenReturn(genre);
        when(genreMapper.entityToDto(genre)).thenReturn(genreResponseDto);

        // When
        GenreResponseDto result = genreService.createGenre(genreRequestDto);

        // Then
        assertEquals(genreResponseDto, result);
        verify(genreMapper).dtoToEntity(genreRequestDto);
        verify(genreRepository).save(genre);
        verify(genreMapper).entityToDto(genre);
    }

    @Test
    @DisplayName("Get all genres successfully")
    void getAllGenres_Successfully() {
        // Given
        List<Genre> genres = Arrays.asList(genre);
        List<GenreResponseDto> expectedDtos = Arrays.asList(genreResponseDto);
        
        when(genreRepository.findAll()).thenReturn(genres);
        when(genreMapper.entityToDto(genre)).thenReturn(genreResponseDto);

        // When
        List<GenreResponseDto> result = genreService.getAllGenres();

        // Then
        assertEquals(expectedDtos, result);
        verify(genreRepository).findAll();
        verify(genreMapper).entityToDto(genre);
    }

    @Test
    @DisplayName("Get genre by ID successfully")
    void getGenre_Successfully() {
        // Given
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
        when(genreMapper.entityToDto(genre)).thenReturn(genreResponseDto);

        // When
        GenreResponseDto result = genreService.getGenre(1L);

        // Then
        assertEquals(genreResponseDto, result);
        verify(genreRepository).findById(1L);
        verify(genreMapper).entityToDto(genre);
    }

    @Test
    @DisplayName("Get genre by ID throws RuntimeException when genre not found")
    void getGenre_ThrowsRuntimeException_WhenGenreNotFound() {
        // Given
        when(genreRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> genreService.getGenre(1L));
        verify(genreRepository).findById(1L);
        verifyNoInteractions(genreMapper);
    }

    @Test
    @DisplayName("Update genre successfully")
    void updateGenre_Successfully() {
        // Given
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
        when(genreRepository.save(genre)).thenReturn(genre);
        when(genreMapper.entityToDto(genre)).thenReturn(genreResponseDto);

        // When
        GenreResponseDto result = genreService.updateGenre(1L, genreRequestDto);

        // Then
        assertEquals(genreResponseDto, result);
        assertEquals(genreRequestDto.name(), genre.getName());
        verify(genreRepository).findById(1L);
        verify(genreRepository).save(genre);
        verify(genreMapper).entityToDto(genre);
    }

    @Test
    @DisplayName("Delete genre successfully")
    void deleteGenre_Successfully() {
        // When
        genreService.deleteGenre(1L);

        // Then
        verify(genreRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Get films by genre successfully")
    void getFilmsByGenre_Successfully() {
        // Given
        genre.setFilms(Arrays.asList(film));
        List<FilmResponseDto> expectedDtos = Arrays.asList(filmResponseDto);
        
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
        when(filmMapper.entityToDto(film)).thenReturn(filmResponseDto);

        // When
        List<FilmResponseDto> result = genreService.getFilmsByGenre(1L);

        // Then
        assertEquals(expectedDtos, result);
        verify(genreRepository).findById(1L);
        verify(filmMapper).entityToDto(film);
    }

    @Test
    @DisplayName("Get films by genre throws RuntimeException when genre not found")
    void getFilmsByGenre_ThrowsRuntimeException_WhenGenreNotFound() {
        // Given
        when(genreRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> genreService.getFilmsByGenre(1L));
        verify(genreRepository).findById(1L);
        verifyNoInteractions(filmMapper);
    }
} 