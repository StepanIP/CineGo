package org.example.payment.facade;

import com.paypal.api.payments.Payment;
import lombok.RequiredArgsConstructor;
import org.example.payment.configuration.PayPalConfig;
import org.example.payment.domain.dto.PaymentRequestDto;
import org.example.payment.domain.model.PaymentStatus;
import org.example.payment.service.PaymentService;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

@Service
@RequiredArgsConstructor
public class PaymentFacade {

    private final PaymentService paymentService;

    private final PayPalConfig.RedirectUrl redirectUrl;

    public RedirectView initiatePayment(PaymentRequestDto dto) {
        Payment payment = paymentService.createAndRedirect(dto);
        for (com.paypal.api.payments.Links link : payment.getLinks()) {
            if ("approval_url".equals(link.getRel())) {
                return new RedirectView(link.getHref());
            }
        }
        return new RedirectView(redirectUrl.getError());
    }

    public void processSuccess(Long paymentId, String paypalPaymentId, String payerId) {
        paymentService.completePayment(paymentId, paypalPaymentId, payerId);
    }

    public void processCancel(Long paymentId) {
        paymentService.cancelPayment(paymentId, PaymentStatus.CANCELED);
    }

    public void processError(Long paymentId) {
        paymentService.cancelPayment(paymentId, PaymentStatus.FAILED);
    }
}
