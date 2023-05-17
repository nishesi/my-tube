FROM openjdk:21-slim-buster
COPY target/MyTube-2.0.jar /app-service/MyTube-2.0.jar
WORKDIR /app-service
EXPOSE 8080

ENTRYPOINT ["java","-jar","MyTube-2.0.jar"]