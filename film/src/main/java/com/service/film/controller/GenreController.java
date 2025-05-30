package com.service.film.controller;

import com.service.film.dto.FilmResponseDto;
import com.service.film.dto.GenreRequestDto;
import com.service.film.dto.GenreResponseDto;
import com.service.film.service.GenreService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/genres")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping
    @ResponseStatus(code = org.springframework.http.HttpStatus.CREATED)
    public GenreResponseDto createGenre(@RequestBody GenreRequestDto dto) {
        return genreService.createGenre(dto);
    }

    @GetMapping
    public List<GenreResponseDto> getAllGenres() {
        return genreService.getAllGenres();
    }

    @GetMapping("/{id}")
    public GenreResponseDto getGenre(@PathVariable Long id) {
        return genreService.getGenre(id);
    }

    @PutMapping("/{id}")
    public GenreResponseDto updateGenre(@PathVariable Long id, @RequestBody GenreRequestDto dto) {
        return genreService.updateGenre(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = org.springframework.http.HttpStatus.NO_CONTENT)
    public void deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
    }

    @GetMapping("/{id}/films")
    public List<FilmResponseDto> getFilmsByGenre(@PathVariable Long id) {
        return genreService.getFilmsByGenre(id);
    }
}

