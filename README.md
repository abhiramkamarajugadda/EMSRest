# EMSRest: Employee Management REST API

A Spring Boot REST API for employee CRUD operations, backed by MySQL through Spring Data JPA and documented with Swagger UI (springdoc-openapi).

## Features

- Create, list, fetch by id, update and delete employees
- `ResponseEntity` responses with explicit HTTP status codes (201, 200, 302, 400, 404)
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
| POST | `/api/ems/employees` | Add an employee (JSON body) | 201 Created | 400 Bad Request |
| GET | `/api/ems/employees` | List all employees | 302 Found | 404 Not Found |
| GET | `/api/ems/employees/{id}` | Get an employee by id | 302 Found | 404 Not Found |
| PUT | `/api/ems/employees` | Update an employee (JSON body with `id`) | 200 OK | 400 Bad Request |
| DELETE | `/api/ems/employees/{id}` | Delete an employee | 200 OK | 400 Bad Request |

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

## Author

**Abhiram Kamarajugadda**
GitHub: [abhiramkamarajugadda](https://github.com/abhiramkamarajugadda) | Portfolio: [abhiramkamarajugadda.github.io](https://abhiramkamarajugadda.github.io)
