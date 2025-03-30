FROM maven:3-eclipse-temurin-21-alpine
WORKDIR /home/app

ENTRYPOINT cd app && mvn spring-boot:run --also-make