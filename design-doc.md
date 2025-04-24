# Book Management System

**Author(s):** [Karla Sequen](mailto:hi@karlasequen.com)

**Status:** In Progress

**Last Updated:** 2025-04-22

## Table of Contents

1. [Summary](#summary)
2. [Context](#context)
3. [Detailed Design](#detailed-design)
   - [Proposed Solution](#proposed-solution)
4. [Testing and Validation](#testing-and-validation)
5. [Future Considerations](#future-considerations)

---

## Summary

This project is a simple Book Management System designed to manage books and track which users have borrowed them. It provides functionality to list books, check availability, lend them to users, and see who currently holds a specific book.

## Context

The system is built as part of a bootcamp final project and is intended to demonstrate the use of Java and Spring Boot in a microservices architecture. It consists of two RESTful API microservices: one for managing users and one for managing books.

## Detailed Design


### Proposed Solution

The system will be divided into two microservices:

- **User Service**: Handles CRUD operations for users. Each users has basic attributes such as ID, name, and email.

- **Book Service**: Handles CRUD operations for books. Each book has attributes like ID, title, author, and a status indicating whether it is available or currently borrowed. If borrowed, the book record will include the ID of the users who borrowed it.

The services will communicate via REST API. For example, when a book is lent, the Book Service will validate the users by calling the User Service.

Each service will have its own database to maintain loose coupling and ensure microservice independence.

## Testing and Validation

The solution will follow a structured testing process to ensure reliability and correctness of each microservice.

- **Unit Tests**: Each service will include unit tests for its core business logic. This ensures that individual components work as expected in isolation.

- **Edge Cases**: Specific scenarios such as lending an already borrowed book, deleting a users who has borrowed books, or lending to a non-existent users will be tested to validate system behavior under unusual or incorrect inputs.

- **Metrics**: Basic metrics such as API response time, number of books lent, and system errors will be monitored using Spring Actuator or similar tools, to ensure the services remain healthy and performant.

---

## Future Considerations

- Implement authentication and authorization (e.g., using JWT) to secure endpoints.
- Add pagination and filtering for listing books and users.
- Integrate a front-end interface or mobile client.
- Enable service discovery and load balancing using Spring Cloud or similar tools.
- Introduce message queues (e.g., RabbitMQ, Kafka) for asynchronous operations.

---
