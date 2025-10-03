# E-Commerce Microservices Project

This is a **Dockerized E-Commerce microservices project** built with **Java Spring Boot**, **PostgreSQL**, and **Kafka**. The project demonstrates a role-based authentication system, event-driven architecture using Kafka, and inter-service communication between **Auth Service** and **Order Service**.

---

## **Services Overview**

1. **Auth Service**
    - Handles user registration and login.
    - Generates JWT tokens for authentication.
    - Publishes user login events to Kafka (`user-events` topic).

2. **Order Service**
    - Listens to Kafka `user-events` topic for user login events.
    - Stores user session details in PostgreSQL.
    - Processes orders (basic structure implemented).

3. **Kafka & Zookeeper**
    - Kafka is used for messaging between services.
    - Zookeeper manages Kafka broker.

4. **Databases**
    - PostgreSQL is used for persistence for both Auth and Order services.

5. **Kafdrop**
    - Kafka web UI to monitor topics, consumers, and messages.
    - Accessible on port `9000`.

---

## **Prerequisites**

- Docker & Docker Compose installed
- Java 17
- Maven (optional if building Docker images inside containers)

---

## **Setup Instructions**

1. Clone the repository:
   ```bash
   git clone <repo-url>
   cd project

2. Start all services using Docker Compose:
   ```bash
   docker-compose up -d --build

3. Verify containers are running:
   ```bash
   docker ps
4. Access Kafdrop to monitor Kafka:
    ```bash
   http://localhost:9000
Environment Variables (Docker)

Defined in docker-compose.yml:

Service	Env Variable	Description
auth-service	SPRING_DATASOURCE_URL	PostgreSQL connection URL
auth-service	SPRING_DATASOURCE_USERNAME	DB username
auth-service	SPRING_DATASOURCE_PASSWORD	DB password
auth-service	SPRING_KAFKA_BOOTSTRAP_SERVERS	Kafka bootstrap server for producer/consumer
order-service	SPRING_DATASOURCE_URL	PostgreSQL connection URL
order-service	SPRING_DATASOURCE_USERNAME	DB username
order-service	SPRING_DATASOURCE_PASSWORD	DB password
order-service	SPRING_KAFKA_BOOTSTRAP_SERVERS	Kafka bootstrap server for producer/consumer
Project Endpoints
Auth Service (8081)
Endpoint	Method	Description
/auth/register	POST	Register new user
/auth/login	POST	Login user and generate JWT
Order Service (8082)
Endpoint	Method	Description
/orders	POST/GET	Manage orders (basic)
Kafka Topics

user-events → Auth service publishes user login events, Order service consumes and stores session data.

Important Notes

Bootstrap Servers

For Docker containers, always use internal Docker hostname: kafka:29092.

localhost:9092 works only on host machine.

Docker Networking

Each service connects via Docker network using service name as hostname.

Avoid localhost inside containers for inter-service Kafka communication.

Building & Running

docker-compose down
docker-compose up -d --build


Kafka Debugging

Check Kafdrop UI (localhost:9000) for messages.

Ensure consumer group ID matches in consumer configuration.

Logs & Troubleshooting

Auth Service produces messages:

=== KAFKA MESSAGE SENT ===


Order Service consumes messages:

=== KAFKA MESSAGE RECEIVED ===
USER_LOGGED_IN consumed and saved for userId: <id>


Common issues:

Messages not received → Check bootstrap server & advertised listeners in Kafka.

Docker containers cannot reach Kafka → Use internal service hostname (kafka:29092).

Tech Stack

Java 17 + Spring Boot

PostgreSQL 15

Kafka 7.5.0 + Zookeeper

Docker & Docker Compose

Kafdrop (Kafka UI)