package com.service.film.controller;

import com.service.film.dto.GenreRequestDto;
import com.service.film.service.FilmService;
import com.service.film.dto.CreatedFilmDto;
import com.service.film.dto.FilmResponseDto;
import com.service.film.dto.FilmRequestDto;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @PostMapping
    public ResponseEntity<CreatedFilmDto> saveFilm(@RequestBody FilmRequestDto filmRequestDto) {
        CreatedFilmDto createdFilm = filmService.saveFilm(filmRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFilm);
    }

    @GetMapping
    public ResponseEntity<List<FilmResponseDto>> findAll() {
        List<FilmResponseDto> allFilms = filmService.findAllFilms();
        return ResponseEntity.ok(allFilms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmResponseDto> findById(@PathVariable Long id) {
        FilmResponseDto filmResponseDto = filmService.findFilmById(id);
        return ResponseEntity.ok(filmResponseDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<FilmResponseDto>> getFilmsByGenre(@RequestParam("genre") String genreName) {
        List<FilmResponseDto> films = filmService.findFilmsByGenreName(genreName);
        return ResponseEntity.ok(films);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable Long id) {
        filmService.deleteFilm(id);
        return ResponseEntity.noContent().build();
    }
}
