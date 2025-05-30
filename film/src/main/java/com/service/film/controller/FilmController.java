package com.service.film.controller;

import com.service.film.dto.GenreRequestDto;
import com.service.film.service.FilmService;
import com.service.film.dto.CreatedFilmDto;
import com.service.film.dto.FilmResponseDto;
import com.service.film.dto.FilmRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @PostMapping
    @ResponseStatus(code = org.springframework.http.HttpStatus.CREATED)
    public CreatedFilmDto saveFilm(@RequestBody FilmRequestDto filmRequestDto) {
        return filmService.saveFilm(filmRequestDto);
    }

    @GetMapping
    public List<FilmResponseDto> findAll() {
        return filmService.findAllFilms();
    }

    @GetMapping("/{id}")
    public FilmResponseDto findById(@PathVariable Long id) {
        return filmService.findFilmById(id);
    }

    @GetMapping("/")
    public List<FilmResponseDto> getFilmsByGenre(@RequestParam("genre") String genreName) {
        return filmService.findFilmsByGenreName(genreName);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = org.springframework.http.HttpStatus.NO_CONTENT)
    public void deleteFilm(@PathVariable Long id) {
        filmService.deleteFilm(id);
    }
}
