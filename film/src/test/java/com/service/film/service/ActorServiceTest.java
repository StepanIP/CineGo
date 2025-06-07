package com.service.film.service;

import com.service.film.domain.Actor;
import com.service.film.dto.FilmResponseDto;
import com.service.film.exception.exceptions.NotFoundException;
import com.service.film.mapper.FilmMapper;
import com.service.film.repository.ActorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActorServiceTest {

    @Mock
    private ActorRepository actorRepository;

    @Mock
    private FilmMapper filmMapper;

    @InjectMocks
    private ActorService actorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Save actor successfully")
    void saveActorSuccessfully() {
        Actor actor = new Actor(null, "John", "Doe", LocalDate.of(1980, 5, 15), "photo", null);
        Actor savedActor = new Actor(1L, "John", "Doe", LocalDate.of(1980, 5, 15), "photo", null);

        when(actorRepository.save(actor)).thenReturn(savedActor);

        Actor result = actorService.save(actor);

        assertEquals(savedActor, result);
        verify(actorRepository).save(actor);
    }

    @Test
    @DisplayName("Find actor by ID successfully")
    void findActorByIdSuccessfully() {
        Actor actor = new Actor(1L, "John", "Doe", LocalDate.of(1980, 5, 15), "photo", null);

        when(actorRepository.findById(1L)).thenReturn(Optional.of(actor));

        Actor result = actorService.findById(1L);

        assertEquals(actor, result);
        verify(actorRepository).findById(1L);
    }

    @Test
    @DisplayName("Find actor by ID throws exception when not found")
    void findActorByIdThrowsExceptionWhenNotFound() {
        when(actorRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> actorService.findById(1L));

        assertEquals("Actor not found with id 1", exception.getMessage());
        verify(actorRepository).findById(1L);
    }

    @Test
    @DisplayName("Find all actors returns empty list")
    void findAllActorsReturnsEmptyList() {
        when(actorRepository.findAll()).thenReturn(Collections.emptyList());

        List<Actor> result = actorService.findAll();

        assertTrue(result.isEmpty());
        verify(actorRepository).findAll();
    }
}