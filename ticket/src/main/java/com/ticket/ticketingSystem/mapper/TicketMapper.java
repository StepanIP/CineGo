package com.ticket.ticketingSystem.mapper;

import com.ticket.common.dto.TicketBookedDto;
import com.ticket.common.dto.TicketDto;
import com.ticket.ticketingSystem.model.Ticket;
import org.mapstruct.Mapper;

import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface TicketMapper {

    TicketBookedDto bookedTicketToDto(Ticket ticket);

    TicketDto ticketToTicketDto(Ticket ticket);

}
