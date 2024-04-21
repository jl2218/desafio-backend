FROM maven:3.8.4-openjdk-17 AS build

COPY desafio-backend-fcamara/src /app/src
COPY desafio-backend-fcamara/pom.xml /app

WORKDIR /app
RUN mvn clean install

FROM openjdk:17-jdk-alpine

COPY --from=build /app/target/desafio-backend-fcamara-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR /app

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]