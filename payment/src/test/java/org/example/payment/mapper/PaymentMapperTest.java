package org.example.payment.mapper;

import org.example.payment.domain.dto.PaymentRequestDto;
import org.example.payment.domain.model.Payment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class PaymentMapperTest {

    private final PaymentMapper paymentMapper = Mappers.getMapper(PaymentMapper.class);

    @Test
    @DisplayName("Map PaymentRequestDto to Payment entity successfully")
    void mapPaymentRequestDtoToPaymentSuccessfully() {
        // Given
        PaymentRequestDto requestDto = new PaymentRequestDto(
            100.0,
            "USD",
            "Test payment",
            1L
        );

        // When
        Payment payment = paymentMapper.toEntity(requestDto);

        // Then
        assertNotNull(payment);
        assertEquals(100.0, payment.getAmount());
        assertEquals("USD", payment.getCurrency());
        assertEquals("Test payment", payment.getDescription());
        assertEquals(1L, payment.getTicketId());
    }

    @Test
    @DisplayName("Map null PaymentRequestDto to null Payment")
    void mapNullPaymentRequestDtoToNullPayment() {
        assertNull(paymentMapper.toEntity(null));
    }
} 