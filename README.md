# EMSRest: Employee Management REST API

[![CI](https://github.com/abhiramkamarajugadda/EMSRest/actions/workflows/ci.yml/badge.svg)](https://github.com/abhiramkamarajugadda/EMSRest/actions/workflows/ci.yml)

A Spring Boot REST API for employee CRUD operations, backed by MySQL through Spring Data JPA and documented with Swagger UI (springdoc-openapi).

## Live Demo

**Live link:** [abhi-emsrest.onrender.com](https://abhi-emsrest.onrender.com) (opens Swagger UI, where every endpoint can be tried in the browser)

> Note: the free server sleeps when idle, so the first visit may take about a minute to wake up.

The demo runs on an in-memory H2 database seeded with 6 sample employees (fictional). Anything you add or delete resets when the server restarts.

## Features

- Create, list, fetch by id, update and delete employees
- REST status codes: 201 Created with a `Location` header, 200 OK, 204 No Content on delete, 404 Not Found for unknown ids, 400 for an update without an id
- The employee `password` field is write-only and never returned in JSON responses
- `@WebMvcTest` controller tests with Mockito, run by GitHub Actions on every push
- Layered design: controller, service and repository
- Interactive API documentation through Swagger UI

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.1.1, Spring Web MVC (`@RestController`) |
| Persistence | Spring Data JPA, Hibernate, MySQL (`mysql-connector-j`) |
| API docs | springdoc-openapi 3.1.0 (Swagger UI) |
| Demo database | H2 in-memory (`demo` profile) |
| CI/CD | GitHub Actions (`mvn -B verify` on JDK 21, Docker image build) |
| Containers | Multi-stage Dockerfile (JDK 21 build, JRE 21 runtime) |
| Build | Maven (Maven Wrapper included) |

## Project Structure

```text
EMSRest/
  pom.xml
  src/main/java/com/ems/
    EmsRestApplication.java
    controller/EmployeeController.java
    service/EmployeeService.java
    repository/EmployeeRepository.java   (JpaRepository<Employee, Integer>)
    model/Employee.java
  src/main/resources/application.properties
```

## REST API

Base path: `/api/ems/employees`

| Method | Endpoint | Description | Success | Failure |
|---|---|---|---|---|
| POST | `/api/ems/employees` | Add an employee (JSON body) | 201 Created (body and `Location` header) | |
| GET | `/api/ems/employees` | List all employees | 200 OK (empty list when none) | |
| GET | `/api/ems/employees/{id}` | Get an employee by id | 200 OK | 404 Not Found |
| PUT | `/api/ems/employees` | Update an employee (JSON body with `id`) | 200 OK | 400 without `id`, 404 unknown `id` |
| DELETE | `/api/ems/employees/{id}` | Delete an employee | 204 No Content | 404 Not Found |

Sample request body:

```json
{
  "name": "Ravi Kumar",
  "email": "ravi@example.com",
  "password": "secret",
  "age": 25,
  "gender": "Male",
  "mobile": "9000000000",
  "department": "Engineering",
  "address": "Guntur"
}
```

## Data Model

`Employee`: id (generated), name, email, password, age, gender, mobile, department, address.

## Getting Started

**Prerequisites:** JDK 21 and Maven (or the included Maven Wrapper: `sh mvnw spring-boot:run`). MySQL 8 is only needed for the default profile.

```bash
git clone https://github.com/abhiramkamarajugadda/EMSRest.git
cd EMSRest/EMSRest
```

**Quick start, no database (demo profile):**
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=demo
```

**With MySQL (default profile):** the `restems` database is created on first run.
```bash
export DB_USERNAME=root
export DB_PASSWORD=your_mysql_password
mvn spring-boot:run
```

API: `http://localhost:5000/api/ems/employees`
Swagger UI: `http://localhost:5000/swagger-ui.html`

## Running with Docker

```bash
docker build -t emsrest .
docker run -p 5000:5000 emsrest
```

The image starts in the `demo` profile. To use MySQL instead, pass `-e SPRING_PROFILES_ACTIVE= -e DB_URL=... -e DB_USERNAME=... -e DB_PASSWORD=...`.

## Deploy to Render (free)

No database account or secrets are needed.

1. On [Render](https://render.com) choose **New > Blueprint** and pick this repository. `render.yaml` builds the `Dockerfile` on the free plan.
2. The only environment variable is `SPRING_PROFILES_ACTIVE=demo` (already in `render.yaml`). Render sets `PORT`.
3. Deploy, then open `https://<service>.onrender.com/swagger-ui.html`.

## Tests

```bash
cd EMSRest
mvn -B verify
```

Runs 9 tests (controller tests with `@WebMvcTest` and `@MockitoBean`, a context test, and a `demo` profile test that checks the seed data, that passwords are never returned, and the OpenAPI docs), all on in-memory H2, so no MySQL is needed. GitHub Actions runs the same command on JDK 21 and builds the Docker image on every push.

## Author

**Abhiram Kamarajugadda**
GitHub: [abhiramkamarajugadda](https://github.com/abhiramkamarajugadda) | Portfolio: [abhiramkamarajugadda.github.io](https://abhiramkamarajugadda.github.io)
