package com.ticket.ticketingSystem.controller;

import com.ticket.common.dto.TicketBookedDto;
import com.ticket.common.dto.TicketBookingDto;
import com.ticket.ticketingSystem.service.TicketService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book")
public class TicketController {

    private final TicketService service;

    @PostMapping("/{userId}/{screeningId}")
    public ResponseEntity<TicketBookedDto> booking(@PathVariable Long userId,
                                                   @PathVariable Long screeningId,
                                                   @RequestBody TicketBookingDto ticketRequestDto) {
        return new ResponseEntity<>(service.bookTicket(screeningId, userId, ticketRequestDto), HttpStatus.CREATED);
    }
}
