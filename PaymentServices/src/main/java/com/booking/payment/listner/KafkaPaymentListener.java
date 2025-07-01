package com.booking.payment.listner;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.booking.payment.dto.BookingDto;

@Component
public class KafkaPaymentListener {

    private final NewTopic processPaymentTopic;
    private final NewTopic paymentCompletedTopic;
    private final KafkaTemplate<String, BookingDto> kafkaTemplate;

    public KafkaPaymentListener(
            @Qualifier("paymentProcess") NewTopic processPaymentTopic,
            @Qualifier("paymentCompleted") NewTopic paymentCompletedTopic,
            KafkaTemplate<String, BookingDto> kafkaTemplate) {
        this.processPaymentTopic = processPaymentTopic;
        this.paymentCompletedTopic = paymentCompletedTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "process_payment_topic", groupId = "payment-service-group", containerFactory = "kafkaListenerContainerFactory")
    public void listen(BookingDto bookingDto) {
        try {
        	System.out.println("✅ Received from BookingService: " + bookingDto);
            Thread.sleep(5000); // Simulate payment processing
            bookingDto.setStatus("COMPLETED");
            System.out.println("✅ Payment Completed from UPI : " + bookingDto);

            // Send to payment_completed_topic
                Message<BookingDto> message = MessageBuilder.withPayload(bookingDto)
                    .setHeader(KafkaHeaders.TOPIC, paymentCompletedTopic.name())
                    .build();
            kafkaTemplate.send(message);
            System.out.println("✅ Payment Completed message sent to BookingService"); 
        } catch (Exception ex) {
            System.err.println("❌ Error in KafkaPaymentListener: " + ex.getMessage());
        }
    }
}
