# QuestLog (Spring Boot)

A small-but-polished Spring Boot app that feels “complete”:

- REST API (+ Swagger UI)
- Server-rendered UI (Thymeleaf)
- H2 database + JPA
- Validation + ProblemDetail error responses
- Simple analytics (“streaks”)
- Actuator health/info
- Basic security: `/api/**` and `/actuator/**` require HTTP Basic

## Requirements

- Java 21+
- Maven 3.9+

## Run

```bash
mvn spring-boot:run
```

Open:

- UI: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- API base: http://localhost:8080/api
- H2 console: http://localhost:8080/h2-console
- Actuator health: http://localhost:8080/actuator/health

## Credentials

For API (HTTP Basic):

- user: `admin`
- pass: `admin`

(You can change these in `src/main/resources/application.yml`.)

## Quick API demo

```bash
curl -u admin:admin http://localhost:8080/api/quests

curl -u admin:admin -H "Content-Type: application/json" \
  -d '{"title":"Ship something","description":"Make it real","priority":5,"status":"ACTIVE","tags":["spring","focus"]}' \
  http://localhost:8080/api/quests
```

## Tests

```bash
mvn test
```
