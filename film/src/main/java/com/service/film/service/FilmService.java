package com.service.film.service;

import com.service.film.domain.Genre;
import com.service.film.mapper.FilmMapper;
import com.service.film.repository.FilmRepository;
import com.service.film.domain.Film;
import com.service.film.dto.CreatedFilmDto;
import com.service.film.dto.FilmRequestDto;
import com.service.film.dto.FilmResponseDto;
import com.service.film.exception.exceptions.AlreadyExistException;
import com.service.film.exception.exceptions.NotFoundException;
import com.service.film.repository.GenreRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class FilmService {

    private final FilmRepository repository;
    private final FilmMapper mapper;
    private final GenreRepository genreRepository;

    @Transactional
    public CreatedFilmDto saveFilm(FilmRequestDto filmRequestDto) {
        if (repository.existsByTitle(filmRequestDto.title())) {
            throw new AlreadyExistException("The film with title " + filmRequestDto.title() + " already exists.");
        }
        Film film = repository.save(mapper.dtoToEntity(filmRequestDto));
        log.info("Saved film {}", filmRequestDto);
        return mapper.createdEntityToDto(film);
    }

    public FilmResponseDto findFilmById(Long id) {
        Film film = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("The film with id " + id + " not found", id));
        log.info("Found film with ID {}", id);
        return mapper.entityToDto(film);
    }

    public Film findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new NotFoundException("The film with id " + id + " not found", id));
    }

    public List<FilmResponseDto> findAllFilms() {
        log.info("Returning all films");
        return repository.findAll().stream()
            .map(mapper::entityToDto)
            .collect(Collectors.toList());
    }

    public List<FilmResponseDto> findFilmsByGenreName(String genreName) {
        Genre genre = genreRepository.findByName(genreName)
            .orElseThrow(() -> new NotFoundException("Genre not found: " + genreName));
        List<Film> films = repository.findByGenresContaining(genre);
        log.info("Found {} films in ganre {}", films.size(), genre);
        return films.stream()
            .map(mapper::entityToDto)
            .collect(Collectors.toList());
    }

    @Transactional
    public void deleteFilm(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("The film with id " + id + " not found", id);
        }
        repository.deleteById(id);
        log.info("Deleted film with id {}", id);
    }
}

