FROM eclipse-temurin:17-jdk-alpine

WORKDIR /authentication_server

COPY target/AuthenticationService.jar AuthenticationService.jar

EXPOSE 8081 

ENTRYPOINT ["java", "-jar", "AuthenticationService.jar"]