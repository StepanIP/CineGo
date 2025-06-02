package com.service.film.mapper;

import com.service.film.domain.Film;
import com.service.film.dto.CreatedFilmDto;
import com.service.film.dto.FilmRequestDto;
import com.service.film.dto.FilmResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {GenreMapper.class, ActorMapper.class})
public interface FilmMapper {

    @Mapping(target = "durationFilmInMinutes", source = "durationMinutes")
    @Mapping(target = "genres", source = "genres")
    @Mapping(target = "actors", source = "actors")
    @Mapping(target = "trailerUrl", source = "trailerUrl")
    @Mapping(target = "coverImage", source = "coverImage")
    @Mapping(target = "titleImage", source = "titleImage")
    FilmResponseDto entityToDto(Film film);

    CreatedFilmDto createdEntityToDto(Film film);

    @Mapping(target = "durationMinutes", source = "durationFilmInMinutes")
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "actors", ignore = true)
    @Mapping(target = "rating", constant = "0.0")
    @Mapping(target = "trailerUrl", source = "trailerUrl")
    @Mapping(target = "coverImage", source = "coverImage")
    @Mapping(target = "titleImage", source = "titleImage")
    Film dtoToEntity(FilmRequestDto dto);
}
