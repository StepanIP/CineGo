package com.service.film.controller;

import com.service.film.domain.Actor;
import com.service.film.dto.ActorRequestDto;
import com.service.film.dto.ActorResponseDto;
import com.service.film.dto.FilmResponseDto;
import com.service.film.mapper.ActorMapper;
import com.service.film.service.ActorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActorControllerTest {

    @Mock
    private ActorService actorService;

    @Mock
    private ActorMapper actorMapper;

    @InjectMocks
    private ActorController actorController;

    private ActorRequestDto actorRequestDto;

    private Actor actor;

    private ActorResponseDto actorResponseDto;

    private List<Actor> actors;

    private List<ActorResponseDto> actorResponseDtos;

    private List<FilmResponseDto> filmResponseDtos;

    @BeforeEach
    void setUp() {
        LocalDate birthDate = LocalDate.of(1980, 1, 1);

        actorRequestDto = new ActorRequestDto("John", "Doe", birthDate, "photo.jpg");

        actor = Actor.builder()
            .id(1L)
            .firstName("John")
            .lastName("Doe")
            .birthDate(birthDate)
            .photo("photo.jpg")
            .build();

        actorResponseDto = new ActorResponseDto(1L, "John", "Doe", "photo.jpg");

        actors = Arrays.asList(actor);
        actorResponseDtos = Arrays.asList(actorResponseDto);

        filmResponseDtos = Arrays.asList(
            new FilmResponseDto(1L, "Test Film", "Description", Arrays.asList(), 120, null,
                "Director", 4.5, "Country", 16, "trailer", "cover", "title", Arrays.asList())
        );
    }

    @Test
    void createActor_ShouldReturnCreatedActor() {
        // Given
        when(actorMapper.dtoToEntity(actorRequestDto)).thenReturn(actor);
        when(actorService.save(actor)).thenReturn(actor);
        when(actorMapper.entityToDto(actor)).thenReturn(actorResponseDto);

        // When
        ActorResponseDto result = actorController.createActor(actorRequestDto);

        // Then
        assertEquals(actorResponseDto, result);
        verify(actorMapper).dtoToEntity(actorRequestDto);
        verify(actorService).save(actor);
        verify(actorMapper).entityToDto(actor);
    }

    @Test
    void getActorById_ShouldReturnActor() {
        // Given
        Long actorId = 1L;
        when(actorService.findById(actorId)).thenReturn(actor);
        when(actorMapper.entityToDto(actor)).thenReturn(actorResponseDto);

        // When
        ActorResponseDto result = actorController.getActorById(actorId);

        // Then
        assertEquals(actorResponseDto, result);
        verify(actorService).findById(actorId);
        verify(actorMapper).entityToDto(actor);
    }

    @Test
    void getAllActors_ShouldReturnListOfActors() {
        // Given
        when(actorService.findAll()).thenReturn(actors);
        when(actorMapper.entityToDto(actor)).thenReturn(actorResponseDto);

        // When
        List<ActorResponseDto> result = actorController.getAllActors();

        // Then
        assertEquals(actorResponseDtos, result);
        verify(actorService).findAll();
        verify(actorMapper).entityToDto(actor);
    }

    @Test
    void updateActor_ShouldReturnUpdatedActor() {
        // Given
        Long actorId = 1L;
        when(actorMapper.dtoToEntity(actorRequestDto)).thenReturn(actor);
        when(actorService.update(actorId, actor)).thenReturn(actor);
        when(actorMapper.entityToDto(actor)).thenReturn(actorResponseDto);

        // When
        ActorResponseDto result = actorController.updateActor(actorId, actorRequestDto);

        // Then
        assertEquals(actorResponseDto, result);
        verify(actorMapper).dtoToEntity(actorRequestDto);
        verify(actorService).update(actorId, actor);
        verify(actorMapper).entityToDto(actor);
    }

    @Test
    void deleteActor_ShouldCallServiceDelete() {
        // Given
        Long actorId = 1L;

        // When
        actorController.deleteActor(actorId);

        // Then
        verify(actorService).delete(actorId);
    }

    @Test
    void getFilmsByActor_ShouldReturnFilmsList() {
        // Given
        Long actorId = 1L;
        when(actorService.findFilmsByActorId(actorId)).thenReturn(filmResponseDtos);

        // When
        List<FilmResponseDto> result = actorController.getFilmsByActor(actorId);

        // Then
        assertEquals(filmResponseDtos, result);
        verify(actorService).findFilmsByActorId(actorId);
    }
} 