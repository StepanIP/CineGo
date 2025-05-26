package com.service.film.controller;

import com.service.film.domain.Actor;
import com.service.film.dto.ActorRequestDto;
import com.service.film.dto.ActorResponseDto;
import com.service.film.dto.FilmResponseDto;
import com.service.film.mapper.ActorMapper;
import com.service.film.service.ActorService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/actors")
@RequiredArgsConstructor
public class ActorController {

    private final ActorService actorService;

    private final ActorMapper actorMapper;

    @PostMapping
    public ResponseEntity<ActorResponseDto> createActor(@RequestBody ActorRequestDto requestDto) {
        Actor actor = actorMapper.dtoToEntity(requestDto);
        Actor saved = actorService.save(actor);
        return new ResponseEntity<>(actorMapper.entityToDto(saved), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActorResponseDto> getActorById(@PathVariable Long id) {
        Actor actor = actorService.findById(id);
        return ResponseEntity.ok(actorMapper.entityToDto(actor));
    }

    @GetMapping
    public ResponseEntity<List<ActorResponseDto>> getAllActors() {
        List<Actor> actors = actorService.findAll();
        List<ActorResponseDto> dtos = actors.stream()
            .map(actorMapper::entityToDto)
            .toList();
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActorResponseDto> updateActor(@PathVariable Long id, @RequestBody ActorRequestDto requestDto) {
        Actor updatedEntity = actorMapper.dtoToEntity(requestDto);
        Actor updated = actorService.update(id, updatedEntity);
        return ResponseEntity.ok(actorMapper.entityToDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActor(@PathVariable Long id) {
        actorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/films")
    public ResponseEntity<List<FilmResponseDto>> getFilmsByActor(@PathVariable Long id) {
        List<FilmResponseDto> films = actorService.findFilmsByActorId(id);
        return ResponseEntity.ok(films);
    }
}
