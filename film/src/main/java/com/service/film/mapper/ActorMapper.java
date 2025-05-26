package com.service.film.mapper;

import com.service.film.domain.Actor;
import com.service.film.dto.ActorRequestDto;
import com.service.film.dto.ActorResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActorMapper {

    Actor dtoToEntity(ActorRequestDto dto);

    ActorResponseDto entityToDto(Actor actor);
}
