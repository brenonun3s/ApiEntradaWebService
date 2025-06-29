FROM ubuntu:latest AS build
LABEL authors="breno.santos"

RUN apt-get update && apt-get install -y openjdk-21-jdk maven

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim

WORKDIR /app

EXPOSE 8080

COPY --from=build /app/target/demo-1.0-SNAPSHOT-runner.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
