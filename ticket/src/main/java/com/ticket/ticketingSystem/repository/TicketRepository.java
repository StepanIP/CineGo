package com.ticket.ticketingSystem.repository;

import java.util.List;

import com.ticket.ticketingSystem.model.Ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByUserId(Long userId);
}
