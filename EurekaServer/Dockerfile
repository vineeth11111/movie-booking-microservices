FROM eclipse-temurin:17-jdk-alpine

WORKDIR /eureka_server

COPY target/EurekaServer.jar EurekaServer.jar

EXPOSE 8761 

ENTRYPOINT ["java", "-jar", "EurekaServer.jar"]