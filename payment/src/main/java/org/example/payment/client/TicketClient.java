package org.example.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "screening-service", url = "/api/v1/tickets")
public interface TicketClient {

    @PostMapping("/send/{ticketId}")
    void sendTicketToUser(@PathVariable Long ticketId);

    @DeleteMapping("/{ticketId}")
    void deleteTicket(@PathVariable Long ticketId);
}
