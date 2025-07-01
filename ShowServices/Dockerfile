FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/ShowServices.jar ShowServices.jar

EXPOSE 9094 

ENTRYPOINT ["java", "-jar", "ShowServices.jar"]