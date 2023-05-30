FROM azul/zulu-openjdk-alpine:17-jre-headless-latest

# Set the working directory to /app
WORKDIR /app

# Copy the Gradle build files
COPY build.gradle settings.gradle /app/

COPY src /app/src

# Copy the Gradle wrapper files
COPY gradlew /app/
COPY gradle /app/gradle

ENV HUB_ADDRESS=${HUB_ADDRESS:-"https://ci-cd-course-hub-aeyvkm6j4q-lz.a.run.app"}
ENV GITHUB_REPOSITORY_ID=${GITHUB_REPOSITORY_ID:-"0"}
ENV GITHUB_REPOSITORY_OWNER=${GITHUB_REPOSITORY_OWNER:-"Unknown Participant"}

# Download the Gradle dependencies
RUN chmod a+x ./gradlew && \
    ./gradlew --version && \
    ./gradlew clean build --no-daemon && \
    cp /app/build/libs/demo*.jar /app/build/demo.jar

# Expose port 8081 or port provided by env var
EXPOSE ${PORT:-8081}

# Set the command to run the application when the container starts
CMD ["java", "-jar", "/app/build/demo.jar"]
