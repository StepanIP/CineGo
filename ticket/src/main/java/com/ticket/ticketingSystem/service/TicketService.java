package com.ticket.ticketingSystem.service;

import com.ticket.ticketingSystem.exceptions.exception.NotFoundException;
import com.ticket.feignClient.ScreeningClient;
import com.ticket.feignClient.UserClient;
import com.ticket.ticketingSystem.exceptions.exception.TooLateToBookException;

import com.ticket.ticketingSystem.dto.EmailWithTicket;
import com.ticket.ticketingSystem.dto.ScreeningDto;
import com.ticket.ticketingSystem.dto.TicketBookedDto;
import com.ticket.ticketingSystem.dto.TicketBookingDto;
import com.ticket.ticketingSystem.dto.UserResponseDto;
import com.ticket.ticketingSystem.mapper.TicketMapper;
import com.ticket.ticketingSystem.repository.TicketRepository;
import com.ticket.ticketingSystem.kafka.KafkaProducer;
import com.ticket.ticketingSystem.model.Ticket;
import com.ticket.ticketingSystem.ticketPriceCalculator.TicketPriceCalculator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;

import com.ticket.ticketingSystem.model.enums.TicketStatus;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.ticket.ticketingSystem.service.TicketService.ErrorMessages.NOT_FOUND_BY_ID;
import static com.ticket.ticketingSystem.service.TicketService.ErrorMessages.TOO_LATE_TO_BOOK;

@Service
@AllArgsConstructor
@Log4j2
public class TicketService {

    private final TicketRepository ticketRepository;

    private final TicketPriceCalculator ticketPrice;

    private final ScreeningClient screeningClient;

    private final UserClient userClient;

    private final KafkaProducer kafkaProducer;

    private final TicketMapper mapper;

    @Transactional
    public TicketBookedDto bookTicket(Long screeningId, Long userId, TicketBookingDto ticketBookingDto) {
        UserResponseDto user = userClient.findUserById(userId);
        ScreeningDto screening = screeningClient.findScreeningById(screeningId);
        checkBookingTime(screening);
        Ticket newTicket = createNewTicket(screening, user, ticketBookingDto);
        screeningClient.bookingSets(screeningId, ticketBookingDto.rowsNumber(), ticketBookingDto.seatInRow());
        ticketRepository.save(newTicket);
        log.info("Booking ticket with screening ID : {} row: {} seat: {}", screeningId, ticketBookingDto.rowsNumber(), ticketBookingDto.seatInRow());

        kafkaProducer.sendMessage("emailWithTicketTopic", createEmail(user.email(), newTicket));

        return mapper.bookedTicketToDto(newTicket);
    }

    public EmailWithTicket createEmail(String email, Ticket ticket) {

        return new EmailWithTicket(email, mapper.ticketToTicketDto(ticket));
    }

    public Ticket createNewTicket(ScreeningDto screening, UserResponseDto user, TicketBookingDto ticketDto) {

        return Ticket.builder()
            .filmTitle(screening.film().title())
            .screeningDate(screening.date())
            .screeningTime(screening.time())
            .name(concatenateUserName(user.firstName(), user.lastName()))
            .status(TicketStatus.ACTIVE)
            .ticketType(ticketDto.ticketType())
            .ticketPrice(ticketPrice.finalPrice(ticketDto, screening))
            .rowsNumber(ticketDto.rowsNumber())
            .roomNumber(1)
            .currency(ticketDto.currency())
            .seatInRow(ticketDto.seatInRow())
            .userId(user.id())
            .build();
    }

    @Transactional
    public void cancelTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new NotFoundException(NOT_FOUND_BY_ID, ticketId));
        ticket.setStatus(TicketStatus.CANCELLED);
        ticketRepository.delete(ticket);
    }

    public void checkBookingTime(ScreeningDto screening) {

        if (screening.date().isBefore(LocalDate.now())) {
            throw new TooLateToBookException(TOO_LATE_TO_BOOK);
        } else if (screening.date().isEqual(LocalDate.now()) && screening.time().isBefore(LocalTime.now().plusMinutes(15))) {
            throw new TooLateToBookException(TOO_LATE_TO_BOOK);
        }
    }

    public String concatenateUserName(String firstName, String lastName) {
        return firstName + " " + lastName;
    }

    static final class ErrorMessages {

        static final String TOO_LATE_TO_BOOK = "Too late to book a ticket";

        static final String NOT_FOUND_BY_ID = "Not found ticket by Id";
    }
}
