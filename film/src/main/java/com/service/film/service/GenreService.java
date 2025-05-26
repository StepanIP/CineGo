package com.service.film.service;

import com.service.film.domain.Genre;
import com.service.film.dto.FilmResponseDto;
import com.service.film.dto.GenreRequestDto;
import com.service.film.dto.GenreResponseDto;
import com.service.film.mapper.FilmMapper;
import com.service.film.mapper.GenreMapper;
import com.service.film.repository.GenreRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    private final GenreMapper genreMapper;

    private final FilmMapper filmMapper;

    public GenreResponseDto createGenre(GenreRequestDto dto) {
        Genre genre = genreMapper.dtoToEntity(dto);
        Genre saved = genreRepository.save(genre);
        return genreMapper.entityToDto(saved);
    }

    public List<GenreResponseDto> getAllGenres() {
        return genreRepository.findAll().stream()
            .map(genreMapper::entityToDto)
            .collect(Collectors.toList());
    }

    public Genre getGenreEntity(Long id) {
        return genreRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Genre not found"));
    }

    public GenreResponseDto getGenre(Long id) {
        return genreMapper.entityToDto(getGenreEntity(id));
    }

    public GenreResponseDto updateGenre(Long id, GenreRequestDto dto) {
        Genre genre = getGenreEntity(id);
        genre.setName(dto.name());
        Genre updated = genreRepository.save(genre);
        return genreMapper.entityToDto(updated);
    }

    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }

    public List<FilmResponseDto> getFilmsByGenre(Long genreId) {
        Genre genre = getGenreEntity(genreId);
        return genre.getFilms().stream()
            .map(filmMapper::entityToDto)
            .collect(Collectors.toList());
    }
}

