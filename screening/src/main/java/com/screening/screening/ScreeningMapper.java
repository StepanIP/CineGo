package com.screening.screening;

import com.screening.screening.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
interface ScreeningMapper {

    Screening dtoToEntity(ScreeningRequestDto screeningRequestDto);

    ScreeningAvailableSeats screeningToSeatsDto(Screening screening);

    @Mapping(source = "cinema.id", target = "cinemaId")
    CreatedScreeningDto createdEntityToDto(Screening screening);

}
