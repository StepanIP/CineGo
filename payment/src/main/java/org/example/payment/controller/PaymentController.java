package org.example.payment.controller;

import lombok.RequiredArgsConstructor;
import org.example.payment.domain.dto.PaymentRequestDto;
import org.example.payment.facade.PaymentFacade;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final PaymentFacade paymentFacade;

    @PostMapping("/create")
    public RedirectView createPayment(@RequestBody PaymentRequestDto dto) {
        return paymentFacade.initiatePayment(dto);
    }

    @GetMapping("/success/{paymentId}")
    public void handleSuccess(
        @PathVariable("paymentId") Long paymentId,
        @RequestParam("paymentId") String paypalPaymentId,
        @RequestParam("PayerID") String payerId) {
        paymentFacade.processSuccess(paymentId, paypalPaymentId, payerId);
    }

    @GetMapping("/cancel/{paymentId}")
    public void handleCancel(@PathVariable("paymentId") Long paymentId) {
        paymentFacade.processCancel(paymentId);
    }

    @GetMapping("/error/{paymentId}")
    public void handleError(@PathVariable("paymentId") Long paymentId) {
        paymentFacade.processError(paymentId);
    }
}

