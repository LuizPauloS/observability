FROM openjdk:17-jdk-slim-buster

WORKDIR /app

COPY build/libs/*-0.0.1-SNAPSHOT.jar api.jar
ENTRYPOINT java -jar api.jar

EXPOSE 8080