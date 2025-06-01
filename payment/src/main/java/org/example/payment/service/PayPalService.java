package org.example.payment.service;

import java.util.ArrayList;
import java.util.List;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.payment.configuration.PayPalConfig;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayPalService {

    private final PayPalConfig payPalConfig;

    @SneakyThrows
    public Payment createPayment(double total, String currency, String method, String intent, String description, String cancelUrl,
                                               String successUrl) {
        Amount amount = new Amount();
        amount.setTotal(String.valueOf(total));
        amount.setCurrency(currency);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(description);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method);

        Payment payment = new Payment();
        payment.setIntent(intent);
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        payment.setRedirectUrls(new com.paypal.api.payments.RedirectUrls()
                .setCancelUrl(cancelUrl)
                .setReturnUrl(successUrl));

        return payment.create(payPalConfig.apiContext());
    }

    @SneakyThrows
    public Payment executePayment(String paymentId, String payerId) {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        return payment.execute(payPalConfig.apiContext(), paymentExecution);
    }

    public boolean supportedCurrency(String currency) {
        return List.of("USD", "EUR", "GBP").contains(currency.toUpperCase());
    }
}
