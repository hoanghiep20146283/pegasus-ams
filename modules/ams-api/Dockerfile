#### Stage 1: Build the application
FROM openjdk:8-jdk-alpine as build

VOLUME /tmp

ADD /target/*.jar app.jar

ENTRYPOINT java -jar *.jar