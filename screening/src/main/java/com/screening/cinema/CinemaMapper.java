package com.screening.cinema;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CinemaMapper {

    CinemaDto toDto(Cinema cinema);

    Cinema toEntity(CinemaDto dto);
}
