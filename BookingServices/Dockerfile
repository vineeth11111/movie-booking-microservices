FROM eclipse-temurin:17-jdk-alpine

WORKDIR /booking_service

COPY target/BookingService.jar BookingService.jar

EXPOSE 9095 

ENTRYPOINT ["java", "-jar", "BookingService.jar"]