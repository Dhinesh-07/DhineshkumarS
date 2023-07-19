FROM openjdk:17-alpine

WORKDIR /app

COPY Application/target/Application-0.0.1-SNAPSHOT.war /app/Application-docker.war

ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "/app/Application-docker.war" ]