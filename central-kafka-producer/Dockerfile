#
# Build stage
#
# syntax=docker/dockerfile:1
FROM eclipse-temurin:21-jdk-jammy AS build
ENV HOME=/usr/app
RUN mkdir -p "$HOME"
WORKDIR $HOME
ADD .. $HOME
RUN --mount=type=cache,target=/root/.m2 ./mvnw -f "$HOME"/central-kafka-producer/pom.xml clean package -DskipTests spring-boot:repackage

#
# Package stage
#
FROM eclipse-temurin:21-jre-jammy
ARG JAR_FILE=/usr/app/central-kafka-producer/target/*.jar
COPY --from=build $JAR_FILE /app/central-kafka-producer.jar
ENTRYPOINT java -jar /app/central-kafka-producer.jar
