#### Stage 1: Build the application
FROM openjdk:8-jdk-alpine as build

VOLUME /tmp

ADD /modules/ams-api/target/*.jar*.jar app.jar

ENTRYPOINT java -jar *.jar