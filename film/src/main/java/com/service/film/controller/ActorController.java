package com.service.film.controller;

import com.service.film.domain.Actor;
import com.service.film.dto.ActorRequestDto;
import com.service.film.dto.ActorResponseDto;
import com.service.film.dto.FilmResponseDto;
import com.service.film.mapper.ActorMapper;
import com.service.film.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/actors")
@RequiredArgsConstructor
public class ActorController {

    private final ActorService actorService;
    private final ActorMapper actorMapper;

    @PostMapping
    @ResponseStatus(code = org.springframework.http.HttpStatus.CREATED)
    public ActorResponseDto createActor(@RequestBody ActorRequestDto requestDto) {
        Actor actor = actorMapper.dtoToEntity(requestDto);
        Actor saved = actorService.save(actor);
        return actorMapper.entityToDto(saved);
    }

    @GetMapping("/{id}")
    public ActorResponseDto getActorById(@PathVariable Long id) {
        Actor actor = actorService.findById(id);
        return actorMapper.entityToDto(actor);
    }

    @GetMapping
    public List<ActorResponseDto> getAllActors() {
        List<Actor> actors = actorService.findAll();
        return actors.stream()
            .map(actorMapper::entityToDto)
            .toList();
    }

    @PutMapping("/{id}")
    public ActorResponseDto updateActor(@PathVariable Long id, @RequestBody ActorRequestDto requestDto) {
        Actor updatedEntity = actorMapper.dtoToEntity(requestDto);
        Actor updated = actorService.update(id, updatedEntity);
        return actorMapper.entityToDto(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = org.springframework.http.HttpStatus.NO_CONTENT)
    public void deleteActor(@PathVariable Long id) {
        actorService.delete(id);
    }

    @GetMapping("/{id}/films")
    public List<FilmResponseDto> getFilmsByActor(@PathVariable Long id) {
        return actorService.findFilmsByActorId(id);
    }
}
