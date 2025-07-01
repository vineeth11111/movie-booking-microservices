#  Movie Booking Microservices System

A scalable Movie Booking System built with **Spring Boot Microservices**, **Keycloak Authentication**, **Docker**, and **Kubernetes**. 

The system demonstrates real-world synchronous and asynchronous inter-service communication, secure login and token validation via Keycloak, and resilient orchestration with Eureka and API Gateway.

---

## Architecture Overview

[User]
↓
[API Gateway] → [Authentication Service] → [Keycloak]
↓
[Theater Service] ←→ [Movie Service] ←→ [Show Service]
↓ ↓ ↓
[Booking Service] ←→ (Feign) ←→ [Show Service]
↓
(Async) 
↓
[Payment Service] → (Async) → [Booking Service] → [Notification]

## Tech Stack
1) Java 17 + Spring Boot 3.x

2) Spring Cloud (Gateway, Eureka, Feign)

3) Docker + Docker Compose

4) Redis (seat locking)

5) Kafka (event-driven communication)

6) Keycloak (OIDC authentication)

7) MySQL (DB for services & Keycloak)




api-gateway             -> Routes incoming requests to the correct service and validates tokens  
auth-service            -> Interacts with Keycloak to obtain and forward tokens for authentication  
authentication-service  -> Validates JWT tokens from Keycloak and allows/blocks requests  
eureka-server           -> Service registry for dynamic discovery of services  
booking-service         -> Handles seat booking, locks seats in Redis, and interacts with payment  
payment-service         -> Processes payments asynchronously and notifies booking-service  
show-service            -> Manages show schedules, seat layouts, and maps them to movies/theaters  
movie-service           -> Stores movie metadata (name, duration, language, etc.)  
theater-service         -> Contains theater and screen information   
keycloak                -> Open-source identity provider for securing access to services  
keycloak-db             -> MySQL database used internally by Keycloak  
redis                   -> Distributed cache used by booking-service for seat locking  
kafka                   -> Message broker for async communication between services  
zookeeper               -> Kafka dependency for cluster coordination  


This project is set up with Docker Compose for easy local development. It includes all required services: microservices, Keycloak, Redis, Kafka, MySQL, Eureka, and API Gateway.


## Project Workflow

1) theater-service handles all theater and screen-related data.

2) movie-service manages movie information.

3) show-service stores show details like show ID, movie ID, theater ID, screen ID, start time, and a list of seats.

## The main booking logic is handled by booking-service:

1)  It fetches show details using synchronous communication via Feign Client.

2) Once a booking request is received, it locks the selected seats using Redis (distributed cache).

3)  It then stores the booking data in its own database (bookingDB).

4)  After saving the booking, it sends an asynchronous message to payment-service to process the payment.

5)  The payment-service processes the payment and, once completed, sends an asynchronous response back to the booking-service. The booking status is updated to “Payment Completed”. 
 
6)  After that TODO emial notification for booking confirmation

## Authentication and authorization are handled using Keycloak and the authentication-service:

1)  Requests to protected services go through the api-gateway, which forwards them to Keycloak for token validation.

2)  If the token is valid, Keycloak allows the request to proceed to the appropriate service.


If you want more info or want to suggest anything around this, feel free to contact me at vineethbakthavari123@gmail.com

             
  





