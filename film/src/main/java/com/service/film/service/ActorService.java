package com.service.film.service;

import com.service.film.domain.Actor;
import com.service.film.domain.Film;
import com.service.film.dto.FilmResponseDto;
import com.service.film.exception.exceptions.NotFoundException;
import com.service.film.mapper.FilmMapper;
import com.service.film.repository.ActorRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActorService {

    private final ActorRepository actorRepository;

    private final FilmMapper filmMapper;

    public Actor save(Actor actor) {
        return actorRepository.save(actor);
    }

    public Actor findById(Long id) {
        return actorRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Actor not found with id " + id));
    }

    public List<Actor> findAll() {
        return actorRepository.findAll();
    }

    public Actor update(Long id, Actor updatedActor) {
        Actor actor = findById(id);
        actor.setFirstName(updatedActor.getFirstName());
        actor.setLastName(updatedActor.getLastName());
        actor.setBirthDate(updatedActor.getBirthDate());
        return actorRepository.save(actor);
    }

    public void delete(Long id) {
        Actor actor = findById(id);
        actorRepository.delete(actor);
    }

    public List<FilmResponseDto> findFilmsByActorId(Long actorId) {
        Actor actor = findById(actorId);
        return actor.getFilms().stream()
            .map(filmMapper::entityToDto)
            .collect(Collectors.toList());
    }
}
