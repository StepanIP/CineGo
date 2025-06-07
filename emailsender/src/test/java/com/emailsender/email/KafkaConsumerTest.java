package com.emailsender.email;

import com.emailsender.email.dto.ConfirmationEmail;
import com.emailsender.email.dto.EmailWithTicket;
import com.emailsender.email.dto.TicketDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerTest {

    @Mock
    private EmailWithTicketPdf emailWithTicketPdf;

    @Mock
    private ConfirmationEmailSender confirmationEmailSender;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private KafkaConsumer kafkaConsumer;

    private EmailWithTicket emailWithTicket;
    private ConfirmationEmail confirmationEmail;
    private String emailWithTicketJson;
    private String confirmationEmailJson;
    private TicketDto ticketDto;

    @BeforeEach
    void setUp() {
        ticketDto = new TicketDto(
            "John Doe",
            "Movie Title",
            LocalDate.now(),
            LocalTime.now(),
            new BigDecimal("15.00"),
            1,
            5,
            3,
            null,
            null
        );
        emailWithTicket = new EmailWithTicket("test@example.com", ticketDto);
        confirmationEmail = new ConfirmationEmail("test@example.com", "http://example.com/confirm");
        emailWithTicketJson = "{\"email\":\"test@example.com\",\"ticketDto\":{...}}";
        confirmationEmailJson = "{\"to\":\"test@example.com\",\"confirmationLink\":\"http://example.com/confirm\"}";
    }

    @Test
    void emailWithTicketListener_ShouldProcessMessageSuccessfully() throws JsonProcessingException, MessagingException {
        // Given
        when(objectMapper.readValue(emailWithTicketJson, EmailWithTicket.class)).thenReturn(emailWithTicket);

        // When
        kafkaConsumer.emailWithTicketListener(emailWithTicketJson);

        // Then
        verify(objectMapper).readValue(emailWithTicketJson, EmailWithTicket.class);
        verify(emailWithTicketPdf).sendEmailWithPDF(emailWithTicket);
    }

    @Test
    void emailWithTicketListener_ShouldHandleJsonProcessingException() throws JsonProcessingException {
        // Given
        when(objectMapper.readValue(emailWithTicketJson, EmailWithTicket.class))
            .thenThrow(new JsonProcessingException("Invalid JSON") {});

        // When
        kafkaConsumer.emailWithTicketListener(emailWithTicketJson);

        // Then
        verify(objectMapper).readValue(emailWithTicketJson, EmailWithTicket.class);
        verifyNoInteractions(emailWithTicketPdf);
    }

    @Test
    void emailWithTicketListener_ShouldHandleMessagingException() throws JsonProcessingException, MessagingException {
        // Given
        when(objectMapper.readValue(emailWithTicketJson, EmailWithTicket.class)).thenReturn(emailWithTicket);
        doThrow(new MessagingException("Failed to send email"))
            .when(emailWithTicketPdf).sendEmailWithPDF(any(EmailWithTicket.class));

        // When
        kafkaConsumer.emailWithTicketListener(emailWithTicketJson);

        // Then
        verify(objectMapper).readValue(emailWithTicketJson, EmailWithTicket.class);
        verify(emailWithTicketPdf).sendEmailWithPDF(emailWithTicket);
    }

    @Test
    void confirmationEmailListener_ShouldProcessMessageSuccessfully() throws JsonProcessingException, MessagingException {
        // Given
        when(objectMapper.readValue(confirmationEmailJson, ConfirmationEmail.class)).thenReturn(confirmationEmail);

        // When
        kafkaConsumer.ConfirmationEmailListener(confirmationEmailJson);

        // Then
        verify(objectMapper).readValue(confirmationEmailJson, ConfirmationEmail.class);
        verify(confirmationEmailSender).sendConfirmationEmail(confirmationEmail.to(), confirmationEmail.confirmationLink());
    }

    @Test
    void confirmationEmailListener_ShouldHandleJsonProcessingException() throws JsonProcessingException {
        // Given
        when(objectMapper.readValue(confirmationEmailJson, ConfirmationEmail.class))
            .thenThrow(new JsonProcessingException("Invalid JSON") {});

        // When
        kafkaConsumer.ConfirmationEmailListener(confirmationEmailJson);

        // Then
        verify(objectMapper).readValue(confirmationEmailJson, ConfirmationEmail.class);
        verifyNoInteractions(confirmationEmailSender);
    }

    @Test
    void confirmationEmailListener_ShouldHandleMessagingException() throws JsonProcessingException, MessagingException {
        // Given
        when(objectMapper.readValue(confirmationEmailJson, ConfirmationEmail.class)).thenReturn(confirmationEmail);
        doThrow(new MessagingException("Failed to send email"))
            .when(confirmationEmailSender).sendConfirmationEmail(anyString(), anyString());

        // When
        kafkaConsumer.ConfirmationEmailListener(confirmationEmailJson);

        // Then
        verify(objectMapper).readValue(confirmationEmailJson, ConfirmationEmail.class);
        verify(confirmationEmailSender).sendConfirmationEmail(confirmationEmail.to(), confirmationEmail.confirmationLink());
    }
} 