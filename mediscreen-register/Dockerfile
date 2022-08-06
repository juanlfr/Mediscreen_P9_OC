FROM maven:3.6.3 AS maven

WORKDIR /usr/src/app
COPY . /usr/src/app
# Compile and package the application to an executable JAR
RUN mvn package

FROM openjdk:8-jdk-alpine
VOLUME /tmp
# Il y a deux jar dans le dossier target!!
COPY target/mediscreen-register-0.0.1-SNAPSHOT.jar mediscreen-register-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/mediscreen-register-0.0.1-SNAPSHOT.jar"]