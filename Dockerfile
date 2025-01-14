FROM openjdk:17-jdk-slim
RUN apt-get update && apt-get install -y curl && apt-get clean
WORKDIR /app
COPY target/demo-0.0.1-SNAPSHOT.jar /app/demo-client.jar
EXPOSE 8080
EXPOSE 8081
CMD ["java", "-Dserver.port=8080", "-jar", "/app/demo-client.jar"]