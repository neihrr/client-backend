FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/demo-0.0.1-SNAPSHOT.jar /app/demo-client.jar
EXPOSE 8080
CMD ["java", "-Dserver.port=8080", "-jar", "/app/demo-client.jar"]