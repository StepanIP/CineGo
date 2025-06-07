package com.service.film.mapper;

import com.service.film.domain.Genre;
import com.service.film.dto.GenreRequestDto;
import com.service.film.dto.GenreResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class GenreMapperTest {

    private final GenreMapper genreMapper = Mappers.getMapper(GenreMapper.class);

    @Test
    @DisplayName("Map Genre to GenreResponseDto successfully")
    void mapGenreToGenreResponseDtoSuccessfully() {
        // Given
        Genre genre = new Genre(1L, "Action", null);

        // When
        GenreResponseDto responseDto = genreMapper.entityToDto(genre);

        // Then
        assertNotNull(responseDto);
        assertEquals(1L, responseDto.id());
        assertEquals("Action", responseDto.name());
    }

    @Test
    @DisplayName("Map GenreRequestDto to Genre successfully")
    void mapGenreRequestDtoToGenreSuccessfully() {
        // Given
        GenreRequestDto requestDto = new GenreRequestDto("Action");

        // When
        Genre genre = genreMapper.dtoToEntity(requestDto);

        // Then
        assertNotNull(genre);
        assertEquals("Action", genre.getName());
        assertNull(genre.getId()); // ID should be null for new entity
    }

    @Test
    @DisplayName("Map null Genre to null DTO")
    void mapNullGenreToNullDto() {
        assertNull(genreMapper.entityToDto(null));
        assertNull(genreMapper.dtoToEntity(null));
    }
} 