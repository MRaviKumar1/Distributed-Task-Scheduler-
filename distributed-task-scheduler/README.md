# Distributed Task Scheduler (Java Spring Boot)

Microservices demo with:
- **scheduler-service**: Accepts tasks, enqueues to Redis, tracks task status, exposes REST API + simple HTML dashboard.
- **worker-service**: Polls Redis queue, processes tasks, updates status.
- **docker-compose.yml** to run Redis + both services.

Tech:
- Java 17, Spring Boot 2.7.x
- Spring Data Redis (Lettuce)
- Maven
- Docker & docker-compose

Quick start:
1. Install Docker & Docker Compose
2. From repo root:
   docker-compose up --build
3. Open http://localhost:8080 to access scheduler API/dashboard
4. Submit tasks:
   curl -X POST http://localhost:8080/api/v1/tasks -H 'Content-Type: application/json' -d '{"payload":"do-something","delaySeconds":2}'
