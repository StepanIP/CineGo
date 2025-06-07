package com.screening.mapper;

import com.screening.domain.model.Screening;
import com.screening.domain.dto.CreatedScreeningDto;
import com.screening.domain.dto.ScreeningAvailableSeats;
import com.screening.domain.dto.ScreeningRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CinemaMapperHelper.class)
public interface ScreeningMapper {


    @Mapping(source = "cinemaId", target = "cinema")
    Screening dtoToEntity(ScreeningRequestDto dto);

    ScreeningAvailableSeats screeningToSeatsDto(Screening screening);

    @Mapping(source = "cinema.id", target = "cinemaId")
    CreatedScreeningDto createdEntityToDto(Screening screening);

}
