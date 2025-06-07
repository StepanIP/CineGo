package org.example.payment.facade;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import org.example.payment.configuration.PayPalConfig;
import org.example.payment.domain.dto.PaymentRequestDto;
import org.example.payment.domain.model.PaymentStatus;
import org.example.payment.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentFacadeTest {

    @Mock
    private PaymentService paymentService;

    @Mock
    private PayPalConfig.RedirectUrl redirectUrl;

    @InjectMocks
    private PaymentFacade paymentFacade;

    private PaymentRequestDto paymentRequestDto;
    private Payment paypalPayment;

    @BeforeEach
    void setUp() {
        paymentRequestDto = new PaymentRequestDto(100.0, "USD", "Test payment", 1L);
        
        paypalPayment = new Payment();
        Links approvalLink = new Links();
        approvalLink.setRel("approval_url");
        approvalLink.setHref("http://paypal.com/approve");
        paypalPayment.setLinks(Arrays.asList(approvalLink));
    }

    @Test
    @DisplayName("Initiate payment successfully")
    void initiatePayment_Successfully() {
        // Given
        when(paymentService.createAndRedirect(paymentRequestDto)).thenReturn(paypalPayment);

        // When
        RedirectView result = paymentFacade.initiatePayment(paymentRequestDto);

        // Then
        assertNotNull(result);
        assertEquals("http://paypal.com/approve", result.getUrl());
        verify(paymentService).createAndRedirect(paymentRequestDto);
    }

    @Test
    @DisplayName("Process successful payment")
    void processSuccess_Successfully() {
        // Given
        Long paymentId = 1L;
        String paypalPaymentId = "PAY-123";
        String payerId = "PAYER-123";

        // When
        paymentFacade.processSuccess(paymentId, paypalPaymentId, payerId);

        // Then
        verify(paymentService).completePayment(paymentId, paypalPaymentId, payerId);
    }

    @Test
    @DisplayName("Process payment cancellation")
    void processCancel_Successfully() {
        // Given
        Long paymentId = 1L;

        // When
        paymentFacade.processCancel(paymentId);

        // Then
        verify(paymentService).cancelPayment(paymentId, PaymentStatus.CANCELED);
    }

    @Test
    @DisplayName("Process payment error")
    void processError_Successfully() {
        // Given
        Long paymentId = 1L;

        // When
        paymentFacade.processError(paymentId);

        // Then
        verify(paymentService).cancelPayment(paymentId, PaymentStatus.FAILED);
    }
} 