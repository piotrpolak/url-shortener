FROM maven:alpine

WORKDIR /app/

ADD pom.xml pom.xml
ADD src src
ADD templates templates

EXPOSE 8080

ENTRYPOINT mvn spring-boot:run -Pdev