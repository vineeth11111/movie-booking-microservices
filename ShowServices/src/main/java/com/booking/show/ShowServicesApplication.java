package com.booking.show;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ShowServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShowServicesApplication.class, args);
	}

}
