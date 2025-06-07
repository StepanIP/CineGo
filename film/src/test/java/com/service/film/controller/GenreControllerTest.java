package com.service.film.controller;

import com.service.film.dto.FilmResponseDto;
import com.service.film.dto.GenreRequestDto;
import com.service.film.dto.GenreResponseDto;
import com.service.film.service.GenreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenreControllerTest {

    @Mock
    private GenreService genreService;

    @InjectMocks
    private GenreController genreController;

    private GenreRequestDto genreRequestDto;
    private GenreResponseDto genreResponseDto;
    private List<GenreResponseDto> genreResponseDtos;
    private List<FilmResponseDto> filmResponseDtos;

    @BeforeEach
    void setUp() {
        genreRequestDto = new GenreRequestDto("Action");
        genreResponseDto = new GenreResponseDto(1L, "Action");
        genreResponseDtos = Arrays.asList(genreResponseDto);
        filmResponseDtos = Arrays.asList(
            new FilmResponseDto(1L, "Test Film", "Description", Arrays.asList(), 120, null, 
                "Director", 4.5, "Country", 16, "trailer", "cover", "title", Arrays.asList())
        );
    }

    @Test
    void createGenre_ShouldReturnCreatedGenre() {
        // Given
        when(genreService.createGenre(genreRequestDto)).thenReturn(genreResponseDto);

        // When
        GenreResponseDto result = genreController.createGenre(genreRequestDto);

        // Then
        assertEquals(genreResponseDto, result);
        verify(genreService).createGenre(genreRequestDto);
    }

    @Test
    void getAllGenres_ShouldReturnListOfGenres() {
        // Given
        when(genreService.getAllGenres()).thenReturn(genreResponseDtos);

        // When
        List<GenreResponseDto> result = genreController.getAllGenres();

        // Then
        assertEquals(genreResponseDtos, result);
        verify(genreService).getAllGenres();
    }

    @Test
    void getGenre_ShouldReturnGenre() {
        // Given
        Long genreId = 1L;
        when(genreService.getGenre(genreId)).thenReturn(genreResponseDto);

        // When
        GenreResponseDto result = genreController.getGenre(genreId);

        // Then
        assertEquals(genreResponseDto, result);
        verify(genreService).getGenre(genreId);
    }

    @Test
    void updateGenre_ShouldReturnUpdatedGenre() {
        // Given
        Long genreId = 1L;
        when(genreService.updateGenre(genreId, genreRequestDto)).thenReturn(genreResponseDto);

        // When
        GenreResponseDto result = genreController.updateGenre(genreId, genreRequestDto);

        // Then
        assertEquals(genreResponseDto, result);
        verify(genreService).updateGenre(genreId, genreRequestDto);
    }

    @Test
    void deleteGenre_ShouldCallServiceDelete() {
        // Given
        Long genreId = 1L;

        // When
        genreController.deleteGenre(genreId);

        // Then
        verify(genreService).deleteGenre(genreId);
    }

    @Test
    void getFilmsByGenre_ShouldReturnFilmsList() {
        // Given
        Long genreId = 1L;
        when(genreService.getFilmsByGenre(genreId)).thenReturn(filmResponseDtos);

        // When
        List<FilmResponseDto> result = genreController.getFilmsByGenre(genreId);

        // Then
        assertEquals(filmResponseDtos, result);
        verify(genreService).getFilmsByGenre(genreId);
    }
} 