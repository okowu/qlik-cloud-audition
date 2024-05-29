# Cloud Audition Project

## Technical Stack

- Framework: Spring Boot
  - Base Framework to support development using MVC style architecture
  - Communication with database handle by Spring Data JPA
- Database: Postgres
- Database Migration Tool: Liquibase
  - A pre-liquibase script (run before the changelog scripts execution) is defined to create the default schema for the application
  - The database related scripts can be found under `src/main/resources/db/changelog`
- Doc: OpenAPI
  - Configured to generate REST API documentation based on javadoc annotation

## Architecture Overview

- A `MessageController` which will handles message related requests that communicate with the domain via
- A `MessageService` interface to execute core logic on the data received and communicate with the
- `MessageRepository` that handle I/O with the database

## How to run the code in local

1. Fetch the project locally
2. Locate the `build.gradle` file and run the task `runQlickCloudAuditionApplication`
   1. Running this task should build and run the application

## REST API Documentation

Once the application is running the REST API is accessible via these endpoints
- API Docs: http://localhost:8080/v3/api-docs
- API UI: http://localhost:8080/swagger-ui/index.html

## Improvements

1. Missing CI script to build, test and deploy image on dockerhub
2. REST API: the list messages endpoint could be enhanced to support pagination
3. Missing unit tests for MessageServiceImpl