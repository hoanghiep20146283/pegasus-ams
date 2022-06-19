#### Stage 1: Build the application
FROM openjdk:8-jdk-alpine as build

RUN echo "Change first step"

VOLUME /tmp

ADD /target/*.jar app.jar

ENTRYPOINT java -jar *.jar