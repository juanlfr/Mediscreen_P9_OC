FROM openjdk:8-jdk-alpine

COPY ./target/mediscreen-risk-0.0.1.jar /opt/app/

WORKDIR /opt/app/

ENTRYPOINT ["java","-jar","mediscreen-risk-0.0.1.jar"]