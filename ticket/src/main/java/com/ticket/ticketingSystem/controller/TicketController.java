package com.ticket.ticketingSystem.controller;

import com.ticket.ticketingSystem.dto.TicketBookedDto;
import com.ticket.ticketingSystem.dto.TicketBookingDto;
import com.ticket.ticketingSystem.service.TicketService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final TicketService service;

    @PostMapping("create/{userId}/{screeningId}")
    public ResponseEntity<TicketBookedDto> createTicket(@PathVariable Long userId,
                                                        @PathVariable Long screeningId,
                                                        @RequestBody TicketBookingDto ticketRequestDto) {
        return new ResponseEntity<>(service.createTicket(screeningId, userId, ticketRequestDto), HttpStatus.CREATED);
    }

    @PostMapping("/send/{ticketId}")
    public void sendTicket(@PathVariable Long ticketId) {
        service.sendTicket(ticketId);
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long ticketId) {
        service.cancelTicket(ticketId);
        return ResponseEntity.noContent().build();
    }
}
