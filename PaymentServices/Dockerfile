FROM eclipse-temurin:17-jdk-alpine

WORKDIR /eureka_server

COPY target/PaymentServer.jar PaymentServer.jar

EXPOSE 8083 

ENTRYPOINT ["java", "-jar", "PaymentServer.jar"]