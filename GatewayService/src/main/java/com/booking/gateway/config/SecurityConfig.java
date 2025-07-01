package com.booking.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;

import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	 @Bean
	 SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
	        http
	            .csrf(ServerHttpSecurity.CsrfSpec::disable)
	            .authorizeExchange(exchanges -> exchanges
	                .pathMatchers("/user/login").permitAll()  
	                .anyExchange().authenticated()         
	            ) .exceptionHandling(ex -> ex
	                    .authenticationEntryPoint(unauthorizedHandler())  // 401 handler
	                    .accessDeniedHandler(forbiddenHandler())          // 403 handler
	                )
	            .oauth2ResourceServer(oauth2 -> oauth2
	                .jwt(Customizer.withDefaults()) 
	            );

	        return http.build();
	    }
	 @Bean
	 ServerAuthenticationEntryPoint unauthorizedHandler() {
	        return (exchange, ex) -> {
	            String body = "{\"error\": \"Unauthorized\", \"message\": \"JWT token is missing or invalid.\"}";
	            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
	            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
	            return exchange.getResponse()
	                    .writeWith(Mono.just(exchange.getResponse()
	                    .bufferFactory().wrap(body.getBytes())));
	        };
	    }

	    
	    @Bean
	    ServerAccessDeniedHandler forbiddenHandler() {
	        return (exchange, denied) -> {
	            String body = "{\"error\": \"Forbidden\", \"message\": \"You do not have permission to access this resource.\"}";
	            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
	            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
	            return exchange.getResponse()
	                    .writeWith(Mono.just(exchange.getResponse()
	                    .bufferFactory().wrap(body.getBytes())));
	        };
	    }
}
