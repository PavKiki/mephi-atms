FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY build/libs/authentication-service-1.0.0.jar authentication-service.jar
ENTRYPOINT ["java","-jar","/authentication-service.jar"]