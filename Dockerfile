FROM openjdk:17-alpine

WORKDIR /app

COPY Application/target/Application-0.0.1-SNAPSHOT.war /app/Application-docker.war

COPY Application/target/classes/application-prod.properties /app/application-prod.properties
COPY Application/target/classes/application-dev.properties /app/application-dev.properties

ENV AWS_ACCESS_KEY_ID=AKIAQ5SR6EGCMZAZML4B
ENV AWS_SECRET_ACCESS_KEY=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
ENV activatedProperties=${activatedProperties}


EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "/app/Application-docker.war" ]