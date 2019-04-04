FROM java:openjdk-8u111-jdk-alpine as builder

COPY . /app
WORKDIR /app
RUN ./gradlew build --no-daemon

FROM openjdk:8u191-jre-alpine
EXPOSE 8080
COPY --from=builder /app/build/libs/verspaetung.jar /app/
WORKDIR /app
CMD java -jar verspaetung.jar