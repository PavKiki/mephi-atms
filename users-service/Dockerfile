FROM openjdk:17-jdk-slim
RUN apt-get update && apt-get install -y postgresql-client
VOLUME /tmp
COPY build/libs/users-service-1.0.0.jar users-service.jar
ENTRYPOINT ["java","-jar","/users-service.jar"]