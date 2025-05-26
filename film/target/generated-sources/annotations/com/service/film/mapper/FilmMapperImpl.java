package com.service.film.mapper;

import com.service.film.domain.Actor;
import com.service.film.domain.Film;
import com.service.film.domain.Genre;
import com.service.film.dto.ActorResponseDto;
import com.service.film.dto.CreatedFilmDto;
import com.service.film.dto.FilmRequestDto;
import com.service.film.dto.FilmResponseDto;
import com.service.film.dto.GenreResponseDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-24T23:12:04+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2 (Amazon.com Inc.)"
)
@Component
public class FilmMapperImpl implements FilmMapper {

    @Autowired
    private GenreMapper genreMapper;
    @Autowired
    private ActorMapper actorMapper;

    @Override
    public FilmResponseDto entityToDto(Film film) {
        if ( film == null ) {
            return null;
        }

        int durationFilmInMinutes = 0;
        List<GenreResponseDto> genres = null;
        List<ActorResponseDto> actors = null;
        Long id = null;
        String title = null;
        String description = null;
        LocalDate releaseDate = null;
        String director = null;
        Double rating = null;
        String country = null;
        Integer ageRestriction = null;

        if ( film.getDurationMinutes() != null ) {
            durationFilmInMinutes = film.getDurationMinutes();
        }
        genres = genreListToGenreResponseDtoList( film.getGenres() );
        actors = actorListToActorResponseDtoList( film.getActors() );
        id = film.getId();
        title = film.getTitle();
        description = film.getDescription();
        releaseDate = film.getReleaseDate();
        director = film.getDirector();
        rating = film.getRating();
        country = film.getCountry();
        ageRestriction = film.getAgeRestriction();

        FilmResponseDto filmResponseDto = new FilmResponseDto( id, title, description, genres, durationFilmInMinutes, releaseDate, director, rating, country, ageRestriction, actors );

        return filmResponseDto;
    }

    @Override
    public CreatedFilmDto createdEntityToDto(Film film) {
        if ( film == null ) {
            return null;
        }

        Long id = null;
        String title = null;

        id = film.getId();
        title = film.getTitle();

        CreatedFilmDto createdFilmDto = new CreatedFilmDto( id, title );

        return createdFilmDto;
    }

    @Override
    public Film dtoToEntity(FilmRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Film.FilmBuilder film = Film.builder();

        film.durationMinutes( dto.durationFilmInMinutes() );
        film.title( dto.title() );
        film.description( dto.description() );
        film.releaseDate( dto.releaseDate() );
        film.director( dto.director() );
        film.rating( dto.rating() );
        film.country( dto.country() );
        film.ageRestriction( dto.ageRestriction() );

        return film.build();
    }

    protected List<GenreResponseDto> genreListToGenreResponseDtoList(List<Genre> list) {
        if ( list == null ) {
            return null;
        }

        List<GenreResponseDto> list1 = new ArrayList<GenreResponseDto>( list.size() );
        for ( Genre genre : list ) {
            list1.add( genreMapper.entityToDto( genre ) );
        }

        return list1;
    }

    protected List<ActorResponseDto> actorListToActorResponseDtoList(List<Actor> list) {
        if ( list == null ) {
            return null;
        }

        List<ActorResponseDto> list1 = new ArrayList<ActorResponseDto>( list.size() );
        for ( Actor actor : list ) {
            list1.add( actorMapper.entityToDto( actor ) );
        }

        return list1;
    }
}
