FROM openjdk:17-jdk-slim
RUN apt-get update && apt-get install -y postgresql-client
VOLUME /tmp
COPY build/libs/testing-management-service-1.0.0.jar testing-management-service.jar
ENTRYPOINT ["java","-jar","/testing-management-service.jar"]