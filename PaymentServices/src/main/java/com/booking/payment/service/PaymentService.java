package com.booking.payment.service;

import com.booking.payment.dto.PaymentRequest;
import com.booking.payment.dto.PaymentResponse;

public interface PaymentService {
    PaymentResponse processPayment(PaymentRequest request);
}
