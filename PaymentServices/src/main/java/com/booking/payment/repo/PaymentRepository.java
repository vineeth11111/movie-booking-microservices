package com.booking.payment.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
