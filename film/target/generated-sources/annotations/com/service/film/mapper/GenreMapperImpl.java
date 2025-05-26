package com.service.film.mapper;

import com.service.film.domain.Genre;
import com.service.film.dto.GenreRequestDto;
import com.service.film.dto.GenreResponseDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-25T01:12:16+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2 (Amazon.com Inc.)"
)
@Component
public class GenreMapperImpl implements GenreMapper {

    @Override
    public GenreResponseDto entityToDto(Genre genre) {
        if ( genre == null ) {
            return null;
        }

        Long id = null;
        String name = null;

        id = genre.getId();
        name = genre.getName();

        GenreResponseDto genreResponseDto = new GenreResponseDto( id, name );

        return genreResponseDto;
    }

    @Override
    public Genre dtoToEntity(GenreRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Genre.GenreBuilder genre = Genre.builder();

        genre.name( dto.name() );

        return genre.build();
    }
}
