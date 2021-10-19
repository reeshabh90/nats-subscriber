FROM openjdk:8-jdk-alpine
COPY ./target/sub-nats-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
ENTRYPOINT ["java", "-jar", "sub-nats-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080