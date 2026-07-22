# Base image
FROM eclipse-temurin:21-jdk

# Working directory
WORKDIR /app

# Copy the JAR into the container
COPY target/customer-service-0.0.1-SNAPSHOT.jar app.jar

# Expose application port
EXPOSE 8081

# Run the application
ENTRYPOINT ["java","-jar","app.jar"]