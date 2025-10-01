Spring Boot Microservices with Kafka
Project Overview

This project demonstrates an event-driven microservices architecture using Spring Boot, PostgreSQL, and Apache Kafka. It consists of two independent services:

Auth Service – Manages user registration, login, JWT authentication, and maintains user history.

Order Service – Manages order creation, validates user login status, and emits order events.

Both services communicate asynchronously through Kafka events, showing bidirectional integration.

Features / Milestones
1. Auth Service

User Registration (POST /auth/register)

Stores users with encrypted passwords (BCrypt).

User Login (POST /auth/login)

Validates credentials and generates JWT token.

Publishes USER_LOGGED_IN Kafka event.

Sample Event:

{
"event": "USER_LOGGED_IN",
"data": { "userId": "1", "email": "test@example.com" }
}


Consumes ORDER_CREATED events from Order Service to maintain user order history.

2. Order Service

Place Order (POST /orders)

Accepts userId, amount, productName.

Checks if the user has logged in via USER_LOGGED_IN event.

Returns 401 Unauthorized if not logged in.

Saves orders in PostgreSQL.

Publishes ORDER_CREATED Kafka event after order creation.

Sample Event:

{
"event": "ORDER_CREATED",
"data": { "orderId": "1001", "userId": "1", "amount": 500 }
}


Consumes USER_LOGGED_IN events to track logged-in users.

3. Kafka Integration

Auth Service → produces USER_LOGGED_IN.

Order Service → consumes USER_LOGGED_IN.

Order Service → produces ORDER_CREATED.

Auth Service → consumes ORDER_CREATED.

This demonstrates bidirectional event-driven communication.

4. Security

JWT-based authentication and authorization.

Auth Service endpoints secured with Spring Security.

Order creation allowed only for logged-in users.

5. Docker Setup

Docker Compose is used for local development:

PostgreSQL databases for both services.

Kafka broker + Zookeeper.

Kafdrop for Kafka UI (debugging events).

Start all services:

docker-compose up

6. Technology Stack

Java 17 / Spring Boot 3.x

Spring Data JPA

PostgreSQL

Apache Kafka

Spring Security (JWT)

Docker & Docker Compose

Maven

7. End-to-End Flow

Register a user in Auth Service.

Login → USER_LOGGED_IN event is published.

Place an order via Order Service.

Order Service validates login via consumed event.

ORDER_CREATED event is published.

Auth Service consumes ORDER_CREATED → updates user order history.

Databases confirm persisted users and orders.

8. How to Run

Clone the repo.

Start Docker services:

docker-compose up


Start Auth Service (port 8081).

Start Order Service (port 8082).

Test endpoints using Postman or any API client.

9. Notes

Kafka events are JSON serialized.

Retry mechanism added for Kafka consumers.

No SQL queries are written manually; Spring Data JPA handles persistence.