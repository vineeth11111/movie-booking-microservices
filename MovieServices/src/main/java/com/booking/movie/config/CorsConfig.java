package com.booking.movie.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
 
 public void addCorsMappings(CorsRegistry registry) {
     registry.addMapping("/**") // allow all endpoints
             .allowedOrigins("http://localhost:3000") // frontend URL
             .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
             .allowedHeaders("*")
             .allowCredentials(true);
 }
}