# Use the official OpenJDK base image optimized for Alpine Linux
FROM eclipse-temurin:17-jdk-alpine as builder

# Set the working directory
WORKDIR /app

# Copy the gradle wrapper and build configuration files
COPY /tmp/repo_780959726_cd50128c/zboruri/gradlew /app/gradlew
COPY /tmp/repo_780959726_cd50128c/zboruri/gradle /app/gradle
COPY /tmp/repo_780959726_cd50128c/zboruri/build.gradle /app/build.gradle
COPY /tmp/repo_780959726_cd50128c/zboruri/settings.gradle /app/settings.gradle

# Copy all source files
COPY /tmp/repo_780959726_cd50128c/zboruri/src /app/src

# Ensure that the gradle wrapper has executable permissions
RUN chmod +x ./gradlew

# Build the application, generating the JAR file
RUN ./gradlew clean build

# Second stage: Create a minimal Java runtime image
FROM eclipse-temurin:17-jre-alpine

# Set a non-root user for improved security
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Work in the /opt/app directory
WORKDIR /opt/app

# Copy the built application JAR from the build stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the necessary ports, if applicable
# EXPOSE 8080 # Uncomment if the app provides a service on port 8080

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]