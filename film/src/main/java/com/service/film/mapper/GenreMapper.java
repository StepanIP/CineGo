package com.service.film.mapper;

import com.service.film.domain.Genre;
import com.service.film.dto.GenreRequestDto;
import com.service.film.dto.GenreResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    GenreResponseDto entityToDto(Genre genre);

    Genre dtoToEntity(GenreRequestDto dto);

}
