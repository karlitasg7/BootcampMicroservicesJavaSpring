# Book Management System

========================

Check the [design document](docs/design-doc.md) for detailed information about the system architecture, design
decisions, and future considerations.

___

## Project Structure

| Directory                | Description                                                                      |
|--------------------------|----------------------------------------------------------------------------------|
| [api-books](./api-books) | Contains the Book Management Service, responsible for managing books.            |
| [api-users](./api-users) | Contains the User Management Service, responsible for managing users.            |
| [docs](./docs)           | Contains documentation files, including API specifications and design documents. |
| [docker](./docker)       | Contains the docker compose file using the images from docker Hub                |
| `docker-compose.yml`     | Docker Compose file to run both services together.                               |
| `README.md`              | This file, providing an overview of the project.                                 |

___

## Stack used

- Java 21
- Spring Boot 3.4.4
- H2 Database
- Maven

___

## Getting Started

### Prerequisites

- Java 21
- Maven
- Postman or any API testing tool (optional, you can use the swagger UI provided by Spring Boot)
- Docker (optional, for running from images)

___

## [Running the Application](docs/running-app.md)

## [Endpoints](docs/endpoints.md)

## [API Swagger Documentation](docs/swagger.md)

## [Postman Collection](docs/Postman_Collection.json)

## You can use the images from docker hub, visit the next links:

- [api-users](https://hub.docker.com/r/karlitasg7/api-users)
- [api-books](https://hub.docker.com/r/karlitasg7/api-books)

## You can test the APIs from Azure, using the following links:

- [api-users](https://ks-bootcamp-javaspring-api-users.azurewebsites.net/swagger-ui/index.html)
- [api-books](https://ks-bootcamp-javaspring-api-books.azurewebsites.net/swagger-ui/index.html)
