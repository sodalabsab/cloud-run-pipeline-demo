FROM azul/zulu-openjdk-alpine:17-jre-headless-latest

# Set the working directory to /app
WORKDIR /app

ENV HUB_ADDRESS=${HUB_ADDRESS:-"https://ci-cd-course-hub-aeyvkm6j4q-lz.a.run.app"}
ENV GITHUB_REPOSITORY_OWNER=${GITHUB_REPOSITORY_OWNER:-"Unknown Participant"}
ENV DOCKER_IMAGE_TAG=${DOCKER_IMAGE_TAG:-"untagged"}

# Copy the application's jar to the container
COPY build/libs/demo*.jar /app/build/demo.jar

# Expose port 8081 or port provided by env var
EXPOSE ${PORT:-8081}

# Set the command to run the application when the container starts
CMD ["java", "-jar", "/app/build/demo.jar"]
