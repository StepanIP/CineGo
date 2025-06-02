package com.screening.client;

import java.util.List;

import com.screening.domain.dto.TicketResponseDto;
import com.screening.domain.model.TicketStatus;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "ticket-service", url = "${application.config.ticket-url}")
public interface TicketClient {

    @GetMapping("{userId}")
    List<TicketResponseDto> findTicketsByUserId(@PathVariable("id") Long userId);

    @PutMapping("{ticketId}/status/{status}")
    void updateTicketStatus(@PathVariable("ticketId") Long ticketId,
                                 @PathVariable("status") TicketStatus status);
}
