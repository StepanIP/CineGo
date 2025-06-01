package org.example.payment.client;

import org.springframework.stereotype.Component;

@Component
public class TicketClient {

    public void sendTicketToUser(Long ticketId) {
        // TODO: відправити REST запит до ticket-сервісу
        // Наприклад:
        // restTemplate.postForObject(...);
    }
}
