# EMSRest: Employee Management REST API

[![CI](https://github.com/abhiramkamarajugadda/EMSRest/actions/workflows/ci.yml/badge.svg)](https://github.com/abhiramkamarajugadda/EMSRest/actions/workflows/ci.yml)

A Spring Boot REST API for employee CRUD operations, backed by MySQL through Spring Data JPA and documented with Swagger UI (springdoc-openapi).

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

**Prerequisites:** JDK 21, MySQL 8, Maven (or the included Maven Wrapper: `sh mvnw spring-boot:run`).

```bash
git clone https://github.com/abhiramkamarajugadda/EMSRest.git
cd EMSRest/EMSRest
```

1. Create the database: `CREATE DATABASE restems;`
2. Set the database credentials as environment variables (`application.properties` reads `${DB_USERNAME:root}` and `${DB_PASSWORD:}`):
   ```bash
   export DB_USERNAME=root
   export DB_PASSWORD=your_mysql_password
   ```
3. Run:
   ```bash
   mvn spring-boot:run
   ```
4. API: `http://localhost:5000/api/ems/employees`
   Swagger UI: `http://localhost:5000/swagger-ui/index.html`

## Tests

```bash
cd EMSRest
mvn -B verify
```

Runs 7 tests (controller tests with `@WebMvcTest` and `@MockitoBean`, plus a context test on an in-memory H2 database), so no MySQL is needed. GitHub Actions runs the same command on JDK 21 for every push.

## Author

**Abhiram Kamarajugadda**
GitHub: [abhiramkamarajugadda](https://github.com/abhiramkamarajugadda) | Portfolio: [abhiramkamarajugadda.github.io](https://abhiramkamarajugadda.github.io)
