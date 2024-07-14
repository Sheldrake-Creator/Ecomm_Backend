# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the project jar file into the container at /app
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Make port 4545 available to the world outside this container
EXPOSE 4545

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
