FROM openjdk:8-jdk-alpine

COPY ./target/mediscreen-register-0.0.1.jar /opt/app/

WORKDIR /opt/app/

ENTRYPOINT ["java","-jar","mediscreen-register-0.0.1.jar"]