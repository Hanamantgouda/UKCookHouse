# Use official Java 17 image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy Maven build output
COPY target/UKCookHouse-0.0.1-SNAPSHOT.jar app.jar

# Expose Render port
EXPOSE 8080

# Run Spring Boot
ENTRYPOINT ["java","-jar","/app/app.jar"]
