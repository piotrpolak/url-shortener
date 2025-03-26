FROM eclipse-temurin:21 AS builder
WORKDIR /src
COPY . .
RUN ./mvnw clean package
RUN java -Djarmode=tools -jar ./url-shortener-app/target/*.jar extract --layers --launcher --destination ./extracted

FROM eclipse-temurin:21
WORKDIR /home/app
COPY --from=builder /src/extracted/dependencies/ ./
COPY --from=builder /src/extracted/spring-boot-loader/ ./
COPY --from=builder /src/extracted/snapshot-dependencies/ ./
COPY --from=builder /src/extracted/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]