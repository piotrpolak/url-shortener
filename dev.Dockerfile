FROM maven:3-eclipse-temurin-17-alpine
WORKDIR /backend

ENTRYPOINT cd url-shortener-app && mvn spring-boot:run --also-make -PswaggerUI