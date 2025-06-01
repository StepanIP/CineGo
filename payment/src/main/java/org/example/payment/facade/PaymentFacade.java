package org.example.payment.facade;

import com.paypal.api.payments.Payment;
import lombok.RequiredArgsConstructor;
import org.example.payment.domain.dto.PaymentRequestDto;
import org.example.payment.service.PaymentService;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

@Service
@RequiredArgsConstructor
public class PaymentFacade {

    private final PaymentService paymentService;

    public RedirectView initiatePayment(PaymentRequestDto dto) {
        Payment payment = paymentService.createAndRedirect(dto);
        for (com.paypal.api.payments.Links link : payment.getLinks()) {
            if ("approval_url".equals(link.getRel())) {
                System.out.println("Redirect to PayPal for approval: " + link.getHref());
                return new RedirectView(link.getHref());
            }
        }
        return new RedirectView("/api/v1/payment/error");
    }

    public void processSuccess(Long paymentId, String paypalPaymentId, String payerId) {
        paymentService.completePayment(paymentId, paypalPaymentId, payerId);
    }

    public void cancel(Long paymentId) {
        paymentService.cancelPayment(paymentId);
    }
}
