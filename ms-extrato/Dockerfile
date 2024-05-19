FROM amazoncorretto:17-alpine-jdk
COPY target/*.jar app.jar

ENV MONGO_DB_URL=mongodb://ms-extrato-db:27017/ms-extrato-db
ENV KAFKA_BROKER=kafka:9092

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]