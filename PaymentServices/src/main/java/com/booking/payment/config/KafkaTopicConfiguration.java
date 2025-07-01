package com.booking.payment.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {

	@Value("${spring.kafka.template.process_payment_topic}")
	private String paymentProcess;
	
	@Value("${spring.kafka.template.payment_completed_topic}")
	private String paymentCompleted;
	
	@Bean("paymentProcess")
	NewTopic paymentProcessTopic() {
		return TopicBuilder.name(paymentProcess).build();
	}
	
	@Bean("paymentCompleted")
	NewTopic paymentCompletedTopic() {
		return TopicBuilder.name(paymentCompleted).build();
	}
}
