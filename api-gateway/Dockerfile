FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY build/libs/api-gateway-1.0.0.jar api-gateway.jar
ENTRYPOINT ["java","-jar","/api-gateway.jar"]