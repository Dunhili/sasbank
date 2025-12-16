# SAS Example Bank Application

SAS Bank is a modular Spring Boot application for managing user accounts, authentication, and customer information. 
It provides a secure API layer, role‑based access control, and a flexible domain model suitable for internal banking 
tools or administrative dashboards.

# Features
# User Management
- Create, update, deactivate, and delete user accounts
- Secure password handling using BCrypt
- Role‑based authorization
- Automatic audit fields (created by/at, updated by/at)
# Authentication & Security
- HTTP Basic Authentication
- Custom UserDetailsService backed by database entities
- Fine‑grained access control using Spring Security
- Automatic audit population based on the authenticated user

# Tech Stack
- Java 21
- Spring Boot 3.5.8
- PostgreSQL
- Spring Security
- JDBCTemplate
- Maven

# Project Structure
```
sas-bank/
├── src/main/java/com/dunhili/sasbank/
│    ├── application/ # User domain, services, controllers
│    ├── auth/        # Authentication, roles, login, security config
│    ├── audit/       # Audit domain, services, controllers, repositories
│    ├── common/      # Shared utilities, abstract base classes, audit helpers, exceptions
│    └── user/        # User domain, services, controllers, repositories
├── src/main/resources/
│    ├── application.yml
│    └── environments/ # Spring profile configuration files per environment
├── src/main/test/com/dunhili/sasbank/ # Unit tests
└── README.md
```

# API Endpoints
# Authentication
| Endpoint              | Method | Description                   |
|-----------------------|--------|-------------------------------|
| /restapi/login/create | POST   | Create or update a user login |


# Users
| Endpoint       | Method | Description                            |
|----------------|--------|----------------------------------------|
| /restapi/users | GET    | Retrieves a user via user ID.          |
| /restapi/users | POST   | Creates or updates a user via user ID. |
| /restapi/users | DELETE | Deletes a user via user ID.            |

# User Roles
| Role     | Description                                 |
|----------|---------------------------------------------|
| User     | Standard user with no special permissions   |
| Customer | User who is also a customer of the bank.    |
| Manager  | User who has elevated permissions.          |
| Admin    | Super-user with full administrative access. |
| Support  | Support only user with limited access.      |

Unauthorized requests return 401 (unauthenticated) or 403 (forbidden).
