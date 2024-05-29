# Cloud Audition Project

## Technical Stack

- Framework: Spring Boot
- Database: Postgres
- Database Migration Tool: Liquibase
- Doc: OpenAPI

## Architecture Overview

The architecture revolves around the MVC style with:
- A `MessageController` which will handles message related requests

## How to run the code in local

1. Fetch the project locally
2. Locate the `build.gradle` file and run the task `runQlickCloudAuditionApplication`

## REST API Documentation

Once the application is running the REST API is accessible via
these endpoints
- API Docs: http://localhost:8080/v3/api-docs
- API UI: http://localhost:8080/swagger-ui/index.html