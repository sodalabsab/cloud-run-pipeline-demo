# Use a base image with Java 11 installed
FROM openjdk:17-slim-buster

# Set the working directory to /app
WORKDIR /app

# Copy the Gradle build files
COPY build.gradle settings.gradle /app/

COPY src /app/src

# Copy the Gradle wrapper files
COPY gradlew /app/
COPY gradle /app/gradle

# Download the Gradle dependencies
RUN chmod a+x ./gradlew && \
    ./gradlew --version && \
    ./gradlew clean build --no-daemon && \
    cp /app/build/libs/demo*.jar /app/build/demo.jar

# Expose port 8080 or port provided by env var
EXPOSE ${PORT:-8080}

# Set the command to run the application when the container starts
CMD ["java", "-jar", "/app/build/demo.jar"]
