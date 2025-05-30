package com.screening.mapper;

import com.screening.domain.dto.CinemaDto;
import com.screening.domain.model.Cinema;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CinemaMapper {

    CinemaDto toDto(Cinema cinema);

    Cinema toEntity(CinemaDto dto);
}
