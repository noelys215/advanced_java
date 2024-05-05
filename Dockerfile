FROM openjdk:17-jdk-slim

COPY target/D387_sample_code-0.0.2-SNAPSHOT.jar app.jar

WORKDIR /app

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]
