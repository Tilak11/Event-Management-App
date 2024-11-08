# Use an official OpenJDK runtime as the base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file to the container
COPY target/event-registration-service-0.0.1-SNAPSHOT.jar event-registration-service-0.0.1-SNAPSHOT.jar

# Expose the service’s port (replace 8081 with the appropriate port for each service)
EXPOSE 8082

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "event-registration-service-0.0.1-SNAPSHOT.jar"]
