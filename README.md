# cloud-run-pipeline-demo

This purpose of this project is to demonstrate an example CI/CD pipeline using GitHub actions.

## First Steps

Start by creating a fork of this repository under your own GitHub account. From there you can
experiment freely - even deploy it to our cloud (if we give you the necessary credentials)!

### Subsequent Steps

- Add an "action" secret called `GCP_CREDENTIALS` containing a json key
- Enable the GitHub actions workflows
- Run the "Initial Cloud Run Deploy" workflow and make sure that it can
    - Build and push a Docker image
    - Create a new service in Google Cloud Run
    - Deploy and start a new revision using the built image

## Build and Test

The application itself is built with Spring Boot, Java 17, and can be compiled, tested and started
locally:

```
./gradlew build test
java -jar build/libs/demo-0.0.3-SNAPSHOT.jar # or whatever version we're at
```

Alternatively, you can run it in a container:

```
docker build -t demo .
docker run --rm -p 8080:8080 demo
# make sure it's alive
curl localhost:8080
```

## Pipeline and Deployment

The workflows under `.github/workflows` are meant to demonstrate a "basic" or "typical" pipeline
where the service is built, unit-tested, containerized, pushed to an artifact registry and then
deployed to a staging environment. End-to-end tests are then run and, if successful, the service is
deployed to production.

The `deploy-staging` and `deploy-production` jobs have an `output` section which gives you the
publicly accessible URL of the deployed service.

### Running in Google Cloud

To deploy the service, you will need to add a secret to your GitHub repository. Under "Settings", "
Security", "Secrets and variables", "Actions" you should add a new secret with the
name `GCP_CREDENTIALS` and a json-formatted key which will be provided by the instructor.

Those credentials allow the built Docker image to be pushed to a repository in Google Artifact
Registry, and a service to be deployed to Google Cloud Run.
