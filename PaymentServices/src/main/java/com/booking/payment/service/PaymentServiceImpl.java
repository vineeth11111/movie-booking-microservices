package com.booking.payment.service;


import java.util.Random;

import org.springframework.stereotype.Service;

import com.booking.payment.dto.PaymentRequest;
import com.booking.payment.dto.PaymentResponse;
import com.booking.payment.entity.Payment;
import com.booking.payment.enums.PaymentStatus;
import com.booking.payment.repo.PaymentRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    // private final BookingClient bookingClient; // optional if using Feign to notify booking

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        // Simulate random success/failure
        PaymentStatus status = new Random().nextBoolean() ? PaymentStatus.SUCCESS : PaymentStatus.FAILED;

        Payment payment = Payment.builder()
                .bookingId(request.getBookingId())
                .amount(request.getAmount())
                .status(status)
                .build();

        Payment saved = paymentRepository.save(payment);

        // Optionally notify booking-service of payment result
        // if (status == PaymentStatus.SUCCESS) {
        //     bookingClient.confirmBooking(request.getBookingId());
        // } else {
        //     bookingClient.cancelBooking(request.getBookingId());
        // }

        return new PaymentResponse(saved.getId(), saved.getBookingId(), saved.getStatus());
    }
}

