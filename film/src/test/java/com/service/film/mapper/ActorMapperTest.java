package com.service.film.mapper;

import com.service.film.domain.Actor;
import com.service.film.dto.ActorRequestDto;
import com.service.film.dto.ActorResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ActorMapperTest {

    private final ActorMapper actorMapper = Mappers.getMapper(ActorMapper.class);

    @Test
    @DisplayName("Map ActorRequestDto to Actor successfully")
    void mapActorRequestDtoToActorSuccessfully() {
        ActorRequestDto requestDto = new ActorRequestDto("John", "Doe", LocalDate.of(1980, 5, 15), "photo");

        Actor actor = actorMapper.dtoToEntity(requestDto);

        assertEquals("John", actor.getFirstName());
        assertEquals("Doe", actor.getLastName());
        assertEquals(LocalDate.of(1980, 5, 15), actor.getBirthDate());
        assertEquals("photo", actor.getPhoto());
    }

    @Test
    @DisplayName("Map Actor to ActorResponseDto successfully")
    void mapActorToActorResponseDtoSuccessfully() {
        Actor actor = new Actor(1L, "John", "Doe", LocalDate.of(1980, 5, 15), "photo", null);

        ActorResponseDto responseDto = actorMapper.entityToDto(actor);

        assertEquals(1L, responseDto.id());
        assertEquals("John", responseDto.firstName());
        assertEquals("Doe", responseDto.lastName());
    }
}