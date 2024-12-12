FROM maven:3.9.9-eclipse-temurin-23

LABEL MAINTAINER="example name"
LABEL DESCRIPTION="an example description"
LABEL version="0.0.0"
LABEL name="example app name"

ARG APP_DIR=/APP

# directory where either source code resides or i copy my project to 
WORKDIR ${APP_DIR}

# copy required files into the image
COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .
COPY src src
COPY .mvn .mvn 

# package application using RUN directive 
# downloads dependencies defines in pom.xml. then compile and package as jar
RUN mvn package -Dmaven.test.skip=true

RUN apt update && apt install -y curl


ENV SERVER_PORT=3000
EXPOSE ${SERVER_PORT}

HEALTHCHECK --interval=10s --timeout=5s --start-period=3s --retries=3 \
   CMD curl http://localhost:${SERVER_PORT}/health || exit 1

ENTRYPOINT SERVER_PORT=${SERVER_PORT} java -jar target/ssf_person_12-0.0.1-SNAPSHOT.jar