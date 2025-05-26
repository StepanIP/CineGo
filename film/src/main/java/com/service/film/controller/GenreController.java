package com.service.film.controller;

import com.service.film.domain.Film;
import com.service.film.domain.Genre;
import com.service.film.dto.FilmResponseDto;
import com.service.film.dto.GenreRequestDto;
import com.service.film.dto.GenreResponseDto;
import com.service.film.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<GenreResponseDto> createGenre(@RequestBody GenreRequestDto dto) {
        return ResponseEntity.ok(genreService.createGenre(dto));
    }

    @GetMapping
    public ResponseEntity<List<GenreResponseDto>> getAllGenres() {
        return ResponseEntity.ok(genreService.getAllGenres());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreResponseDto> getGenre(@PathVariable Long id) {
        return ResponseEntity.ok(genreService.getGenre(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenreResponseDto> updateGenre(@PathVariable Long id,
                                                        @RequestBody GenreRequestDto dto) {
        return ResponseEntity.ok(genreService.updateGenre(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/films")
    public ResponseEntity<List<FilmResponseDto>> getFilmsByGenre(@PathVariable Long id) {
        return ResponseEntity.ok(genreService.getFilmsByGenre(id));
    }
}

