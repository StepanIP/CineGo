package org.example.payment.repository;

import java.util.Optional;

import org.example.payment.domain.model.Payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPaypalPaymentId(String paymentId);
}
