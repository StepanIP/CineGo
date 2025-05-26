package com.service.film.mapper;

import com.service.film.domain.Actor;
import com.service.film.dto.ActorRequestDto;
import com.service.film.dto.ActorResponseDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-24T22:12:22+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2 (Amazon.com Inc.)"
)
@Component
public class ActorMapperImpl implements ActorMapper {

    @Override
    public Actor dtoToEntity(ActorRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Actor.ActorBuilder actor = Actor.builder();

        actor.firstName( dto.firstName() );
        actor.lastName( dto.lastName() );
        actor.birthDate( dto.birthDate() );

        return actor.build();
    }

    @Override
    public ActorResponseDto entityToDto(Actor actor) {
        if ( actor == null ) {
            return null;
        }

        Long id = null;
        String firstName = null;
        String lastName = null;

        id = actor.getId();
        firstName = actor.getFirstName();
        lastName = actor.getLastName();

        ActorResponseDto actorResponseDto = new ActorResponseDto( id, firstName, lastName );

        return actorResponseDto;
    }
}
