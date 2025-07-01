package com.booking.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((req)->
                 req
                 
                         .anyRequest()
                         .authenticated());
        http
                .oauth2Client(Customizer.withDefaults())
                .oauth2Login(Customizer.withDefaults());


        http.sessionManagement((session)->session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));
        return  http.build();

    }
}
