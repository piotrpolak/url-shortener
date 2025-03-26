FROM maven:3-eclipse-temurin-21-alpine

WORKDIR /backend

COPY url-shortener-app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]

