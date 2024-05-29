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
- A `MessageRepository` that handle I/O with the database

## How to run the code in local

There is a docker-compose file under src/test/resources with a sample database for
testing the app.

Once the application is running the REST API is accessible via these endpoints
- API Docs: http://localhost:8080/v3/api-docs
- API UI: http://localhost:8080/swagger-ui/index.html

The API can be tested with a tool like POSTMAN by looking at the api-docs via the specified URL

## Improvements

1. Missing CI script to build, test and deploy image on dockerhub
2. REST API: the list messages endpoint could be enhanced to support pagination
3. Missing unit tests for MessageServiceImpl