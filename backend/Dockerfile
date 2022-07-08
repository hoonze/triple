# Multi-Stage Build ver
#FROM openjdk:11 AS builder
#COPY gradlew .
#COPY gradle gradle
#COPY build.gradle .
#COPY settings.gradle .
#COPY src src
#RUN chmod +x ./gradlew
#RUN ./gradlew bootjar

#FROM openjdk:11
#COPY --from=builder build/libs/*.jar app.jar
#ENTRYPOINT ["java", "-jar", "/app.jar"]

FROM openjdk:8
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]