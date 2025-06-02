package com.ticket.ticketingSystem.mapper;

import com.ticket.ticketingSystem.dto.TicketBookedDto;
import com.ticket.ticketingSystem.dto.TicketResponseDto;
import com.ticket.ticketingSystem.model.Ticket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    TicketBookedDto bookedTicketToDto(Ticket ticket);

    TicketResponseDto ticketToTicketDto(Ticket ticket);

}
