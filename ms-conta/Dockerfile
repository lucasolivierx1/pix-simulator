FROM amazoncorretto:17-alpine-jdk
COPY target/*.jar app.jar

ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres
ENV POSTGRES_DB_URL=jdbc:postgresql://ms-conta-db:5432/ms-conta-db
ENV KAFKA_BROKER=kafka:9092

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]