package org.example.payment.service;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import jakarta.persistence.EntityNotFoundException;
import org.example.payment.client.CurrenciesClient;
import org.example.payment.client.TicketClient;
import org.example.payment.configuration.PayPalConfig;
import org.example.payment.domain.dto.ExchangeRateRequest;
import org.example.payment.domain.dto.ExchangeRateResponse;
import org.example.payment.domain.dto.PaymentRequestDto;
import org.example.payment.domain.model.PaymentStatus;
import org.example.payment.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PayPalService payPalService;

    @Mock
    private CurrenciesClient currencyClient;

    @Mock
    private TicketClient ticketClient;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PayPalConfig.RedirectUrl redirectUrl;

    @InjectMocks
    private PaymentService paymentService;

    private PaymentRequestDto paymentRequestDto;
    private org.example.payment.domain.model.Payment paymentEntity;
    private Payment paypalPayment;

    @BeforeEach
    void setUp() {
        paymentRequestDto = new PaymentRequestDto(100.0, "USD", "Test payment", 1L);
        
        paymentEntity = org.example.payment.domain.model.Payment.builder()
            .id(1L)
            .amount(100.0)
            .currency("USD")
            .description("Test payment")
            .status(PaymentStatus.PENDING)
            .ticketId(1L)
            .build();

        paypalPayment = new Payment();
        paypalPayment.setId("PAY-123");
        Links link = new Links();
        link.setRel("approval_url");
        link.setHref("http://paypal.com/approve");
        paypalPayment.setLinks(Arrays.asList(link));
    }

    @Test
    @DisplayName("Create payment with supported currency")
    void createAndRedirect_WithSupportedCurrency_Successfully() {
        // Given
        when(payPalService.supportedCurrency("USD")).thenReturn(true);
        when(paymentRepository.save(any())).thenReturn(paymentEntity);
        when(redirectUrl.getCancel()).thenReturn("http://localhost:8080/cancel");
        when(redirectUrl.getSuccess()).thenReturn("http://localhost:8080/success");
        when(payPalService.createPayment(
            anyDouble(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString()
        )).thenReturn(paypalPayment);

        // When
        Payment result = paymentService.createAndRedirect(paymentRequestDto);

        // Then
        assertNotNull(result);
        assertEquals(paypalPayment.getId(), result.getId());
        verify(paymentRepository).save(any());
        verify(payPalService).createPayment(
            eq(100.0), eq("USD"), eq("paypal"), eq("sale"), 
            eq("Test payment"), anyString(), anyString()
        );
    }

    @Test
    @DisplayName("Create payment with unsupported currency")
    void createAndRedirect_WithUnsupportedCurrency_Successfully() {
        // Given
        when(payPalService.supportedCurrency("UAH")).thenReturn(false);
        when(currencyClient.convert(any())).thenReturn(new ExchangeRateResponse("120.0", "EUR"));
        when(paymentRepository.save(any())).thenReturn(paymentEntity);
        when(redirectUrl.getCancel()).thenReturn("http://localhost:8080/cancel");
        when(redirectUrl.getSuccess()).thenReturn("http://localhost:8080/success");
        when(payPalService.createPayment(
            anyDouble(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString()
        )).thenReturn(paypalPayment);

        PaymentRequestDto uahRequest = new PaymentRequestDto(100.0, "UAH", "Test payment", 1L);

        // When
        Payment result = paymentService.createAndRedirect(uahRequest);

        // Then
        assertNotNull(result);
        verify(currencyClient).convert(any(ExchangeRateRequest.class));
        verify(paymentRepository).save(any());
        verify(payPalService).createPayment(
            eq(120.0), eq("EUR"), eq("paypal"), eq("sale"), 
            eq("Test payment"), anyString(), anyString()
        );
    }

    @Test
    @DisplayName("Complete payment successfully")
    void completePayment_Successfully() {
        // Given
        Payment executedPayment = new Payment();
        executedPayment.setState("approved");
        
        when(payPalService.executePayment("PAY-123", "PAYER-123")).thenReturn(executedPayment);
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(paymentEntity));
        when(paymentRepository.save(any())).thenReturn(paymentEntity);

        // When
        paymentService.completePayment(1L, "PAY-123", "PAYER-123");

        // Then
        assertEquals(PaymentStatus.SUCCESS, paymentEntity.getStatus());
        verify(ticketClient).sendTicketToUser(1L);
        verify(paymentRepository).save(paymentEntity);
    }

    @Test
    @DisplayName("Complete payment with failure")
    void completePayment_WithFailure() {
        // Given
        Payment executedPayment = new Payment();
        executedPayment.setState("failed");
        
        when(payPalService.executePayment("PAY-123", "PAYER-123")).thenReturn(executedPayment);
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(paymentEntity));
        when(paymentRepository.save(any())).thenReturn(paymentEntity);

        // When
        paymentService.completePayment(1L, "PAY-123", "PAYER-123");

        // Then
        assertEquals(PaymentStatus.FAILED, paymentEntity.getStatus());
        verify(ticketClient).deleteTicket(1L);
        verify(paymentRepository).save(paymentEntity);
    }

    @Test
    @DisplayName("Cancel payment successfully")
    void cancelPayment_Successfully() {
        // Given
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(paymentEntity));
        when(paymentRepository.save(any())).thenReturn(paymentEntity);

        // When
        paymentService.cancelPayment(1L, PaymentStatus.CANCELED);

        // Then
        assertEquals(PaymentStatus.CANCELED, paymentEntity.getStatus());
        verify(paymentRepository).save(paymentEntity);
        verifyNoInteractions(ticketClient);
    }

    @Test
    @DisplayName("Cancel payment with failure status")
    void cancelPayment_WithFailureStatus() {
        // Given
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(paymentEntity));
        when(paymentRepository.save(any())).thenReturn(paymentEntity);

        // When
        paymentService.cancelPayment(1L, PaymentStatus.FAILED);

        // Then
        assertEquals(PaymentStatus.FAILED, paymentEntity.getStatus());
        verify(paymentRepository).save(paymentEntity);
        verify(ticketClient).deleteTicket(1L);
    }

    @Test
    @DisplayName("Cancel payment throws EntityNotFoundException when payment not found")
    void cancelPayment_ThrowsEntityNotFoundException_WhenPaymentNotFound() {
        // Given
        when(paymentRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, 
            () -> paymentService.cancelPayment(1L, PaymentStatus.CANCELED));
        verifyNoInteractions(ticketClient);
    }
} 