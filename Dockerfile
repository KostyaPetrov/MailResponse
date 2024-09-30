# Use an official OpenJDK image as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application jar to the container
COPY target/backend-0.0.1-SNAPSHOT.jar /app/backend.jar

# Expose the port the app runs on
EXPOSE 10234

# Run the jar file
ENTRYPOINT ["java", "-jar", "backend.jar"]

# Environment variables for the Spring Boot application
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5433/studs
ENV SPRING_DATASOURCE_USERNAME=s339742
ENV SPRING_DATASOURCE_PASSWORD=eMgmoDoZhCcsWa62
ENV SPRING_DATASOURCE_DRIVERCLASSNAME=org.postgresql.Driver
ENV SERVER_PORT=10234