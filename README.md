# 🏦 Bank Management System

A RESTful Bank Management System built using **Java 21**, **Spring Boot**, **Spring Security**, **JWT Authentication**, **Spring Data JPA**, and **MySQL**. This project demonstrates secure authentication, customer management, account management, and banking transactions through REST APIs.

---

## 🚀 Features

### Authentication
- User Registration
- User Login
- JWT Token Generation
- Secure REST APIs using Spring Security

### Customer Management
- Create Customer
- Get Customer by ID
- Get All Customers
- Update Customer
- Delete Customer

### Account Management
- Create Account
- Get Account Details
- Get All Accounts
- Update Account
- Delete Account

### Banking Transactions
- Deposit Money
- Withdraw Money
- Transfer Money
- Transaction History

### Other Features
- Global Exception Handling
- Input Validation
- Swagger API Documentation
- Layered Architecture (Controller → Service → Repository)
- JPA/Hibernate Integration
- MySQL Database

---

# 🛠️ Technologies Used

| Technology | Version |
|------------|----------|
| Java | 21 |
| Spring Boot | 3.4 |
| Spring Security | 6 |
| Spring Data JPA | Hibernate |
| MySQL | 8.x |
| JWT | 0.12.x |
| Maven | Latest |
| Lombok | ✓ |
| Swagger OpenAPI | SpringDoc |
| JUnit 5 | ✓ |
| Mockito | ✓ |

---

# 📁 Project Structure

```
customer-service
│
├── controller
├── service
├── serviceImpl
├── repository
├── entity
├── dto
├── security
├── config
├── exception
├── util
└── CustomerServiceApplication.java
```

---

# ⚙️ Prerequisites

- Java 21
- Maven
- MySQL 8+
- IntelliJ IDEA / Eclipse
- Git

---

# ⚙️ Database Configuration

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bank_customer_db
spring.datasource.username=root
spring.datasource.password=root

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Create database:

```sql
CREATE DATABASE bank_customer_db;
```

---

# ▶️ Run the Project

Clone repository

```bash
git clone https://github.com/yashmundada/bank-management-system.git
```

Go inside project

```bash
cd bank-management-system
```

Run application

```bash
./mvnw spring-boot:run
```

or

Run `CustomerServiceApplication.java` from IntelliJ.

# 👤 Customer APIs

| Method | Endpoint |
|---------|----------|
| POST | /customers |
| GET | /customers |
| GET | /customers/{id} |
| PUT | /customers/{id} |
| DELETE | /customers/{id} |

---

# 💳 Account APIs

| Method | Endpoint |
|---------|----------|
| POST | /accounts |
| GET | /accounts |
| GET | /accounts/{id} |
| PUT | /accounts/{id} |
| DELETE | /accounts/{id} |

Frameworks Used

- JUnit 5
- Mockito

---
## Login API

> Add Postman screenshot here

```
images/login-api.png
```
# 🔮 Future Enhancements

- Microservices Architecture
- Spring Cloud Gateway
- Eureka Server
- OpenFeign
- Resilience4j
- Docker
- Docker Compose
- Kubernetes
- CI/CD Pipeline
- Redis Caching
- Kafka Integration

---

# 👨‍💻 Author

**Yash Mundada**

GitHub:
https://github.com/yashmundada

Project Repository:
https://github.com/yashmundada/bank-management-system

---

# ⭐ If you like this project

Please give this repository a ⭐ on GitHub.
