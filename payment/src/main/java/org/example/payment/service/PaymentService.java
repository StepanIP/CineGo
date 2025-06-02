package org.example.payment.service;

import java.util.Optional;

import com.paypal.api.payments.Payment;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.payment.client.CurrenciesClient;
import org.example.payment.client.TicketClient;
import org.example.payment.configuration.PayPalConfig;
import org.example.payment.domain.dto.ExchangeRateRequest;
import org.example.payment.domain.dto.PaymentRequestDto;
import org.example.payment.domain.model.PaymentStatus;
import org.example.payment.repository.PaymentRepository;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PayPalService payPalService;

    private final CurrenciesClient currencyClient;

    private final TicketClient ticketClient;

    private final PaymentRepository paymentRepository;

    private final PayPalConfig.RedirectUrl redirectUrl;

    public Payment createAndRedirect(PaymentRequestDto dto) {
        String supportedCurrency = payPalService.supportedCurrency(dto.currency())
            ? dto.currency()
            : "EUR";

        ExchangeRateRequest exchangeRateRequest = ExchangeRateRequest.builder()
            .amount(dto.total())
            .from(dto.currency())
            .to(supportedCurrency)
            .build();

        double convertedTotal = supportedCurrency.equals(dto.currency())
            ? dto.total()
            : Double.parseDouble(currencyClient.convert(exchangeRateRequest).currency());

        org.example.payment.domain.model.Payment payment = paymentRepository.save(org.example.payment.domain.model.Payment.builder()
            .amount(convertedTotal)
            .currency(supportedCurrency)
            .description(dto.description())
            .status(PaymentStatus.PENDING)
            .ticketId(dto.ticketId())
            .build());

        return payPalService.createPayment(
            convertedTotal,
            supportedCurrency,
            "paypal",
            "sale",
            dto.description(),
            redirectUrl.getCancel() + "/" + payment.getId(),
            redirectUrl.getSuccess() + "/" + payment.getId()
        );
    }

    public void completePayment(Long paymentId, String paypalPaymentId, String payerId) {
        Payment payment = payPalService.executePayment(paypalPaymentId, payerId);
        Optional<org.example.payment.domain.model.Payment> optional = paymentRepository.findById(paymentId);

        if (optional.isPresent()) {
            org.example.payment.domain.model.Payment entity = optional.get();

            if ("approved".equalsIgnoreCase(payment.getState())) {
                entity.setStatus(PaymentStatus.SUCCESS);
                paymentRepository.save(entity);

                ticketClient.sendTicketToUser(entity.getTicketId());
            } else {
                entity.setStatus(PaymentStatus.FAILED);
                ticketClient.deleteTicket(entity.getTicketId());
                paymentRepository.save(entity);
            }
        }
    }

    public void cancelPayment(Long id, PaymentStatus status) {
        org.example.payment.domain.model.Payment payment = paymentRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + id));

        payment.setStatus(status);
        paymentRepository.save(payment);

        if (status == PaymentStatus.FAILED) {
            ticketClient.deleteTicket(payment.getTicketId());
        }
    }
}
