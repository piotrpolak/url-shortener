FROM maven:3-eclipse-temurin-21-alpine
WORKDIR /backend

ENTRYPOINT cd url-shortener-app && mvn spring-boot:run --also-make -P swaggerUI