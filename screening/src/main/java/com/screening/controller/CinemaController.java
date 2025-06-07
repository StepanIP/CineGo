package com.screening.controller;

import com.screening.domain.dto.CinemaDto;
import com.screening.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cinemas")
@RequiredArgsConstructor
public class CinemaController {

    private final CinemaService cinemaService;

    @GetMapping
    public ResponseEntity<List<CinemaDto>> getAll() {
        return ResponseEntity.ok(cinemaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CinemaDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(cinemaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CinemaDto> create(@RequestBody CinemaDto dto) {
        return ResponseEntity.ok(cinemaService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CinemaDto> update(@PathVariable Long id, @RequestBody CinemaDto dto) {
        return ResponseEntity.ok(cinemaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cinemaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
