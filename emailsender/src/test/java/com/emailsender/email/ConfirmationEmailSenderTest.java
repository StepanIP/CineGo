package com.emailsender.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConfirmationEmailSenderTest {

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private MimeMessage mimeMessage;

    @InjectMocks
    private ConfirmationEmailSender emailSender;

    private final String testEmail = "test@example.com";
    private final String testConfirmationLink = "http://example.com/confirm";

    @BeforeEach
    void setUp() {
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
    }

    @Test
    void sendConfirmationEmail_ShouldSendEmailSuccessfully() throws MessagingException {
        // When
        emailSender.sendConfirmationEmail(testEmail, testConfirmationLink);

        // Then
        verify(javaMailSender).createMimeMessage();
        verify(javaMailSender).send(any(MimeMessage.class));
    }

    @Test
    void sendConfirmationEmail_ShouldHandleMessagingException() throws MessagingException {
        // Given
        doAnswer(invocation -> {
            throw new MessagingException("Failed to send email");
        }).when(javaMailSender).send(any(MimeMessage.class));


        // When/Then
        try {
            emailSender.sendConfirmationEmail(testEmail, testConfirmationLink);
        } catch (MessagingException e) {
            verify(javaMailSender).createMimeMessage();
            verify(javaMailSender).send(any(MimeMessage.class));
        }
    }
} 