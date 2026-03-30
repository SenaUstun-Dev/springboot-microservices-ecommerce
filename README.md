# springboot-microservices-ecommerce
# 🚧 Work in Progress

This project is currently **under active development**. Not everything down there is implemented yet.  

---

# 🧩 Spring Boot Microservices Architecture Practice 
# <sub>*VERSION [refactor / feign -> http interface]*</sub>

> [!NOTE] <sub>*about [feign -> http interface] refactoring*</sub>
> This refactoring will not change the business logic of the order-service, only the mechanism used to communicate with the inventory-service.

Below this readme has:
- **Overview** (short explanation of the project)
- **Tech Stack** (used technologies)
- **Architecture Concepts** (used architecture concepts)
- **Project Structure** (how the project is structured folder wise)
- **Architecture Flow** (how does everything work together)
- **Services** (each service's explanation)

# 📌 Overview

This repository contains a **ecommerce microservices architecture practice project** build with Spring boot and Angular. It contains of 6 microservices: product-service, order-service, inventory-service, notification-service, api-gateway, frontend. 

### Goal

This project is intended for **learning, experimentation, and portfolio demonstration**.

The main purpose of this project is to gain hands-on experience with everything in the **"Tech Stack"** and **"Architecture Concepts"** sections.

---

# 🛠 Tech Stack

### Backend
- **Java 21**
- **Spring Boot 3** <sub>*(Spring Web, Spring Data JPA, Lombok)*</sub>
- **Spring Cloud Gateway MVC** <sub>*(API Gateway)*</sub>
- **Spring Cloud Eureka** <sub>*(Service Discovery)*</sub>
- **Spring HTTP Interface** <sub>*(REST Client)*</sub> <sub>*(refactored from Feign Client)*</sub>
- **Resilience4j** <sub>*(Circuit Breaker & Retries)*</sub>
- **Flyway** <sub>*(Database Migrations)*</sub>
- **Swagger (OpenAPI) v3** <sub>*(API Documentation)*</sub>

### Databases
- **MongoDB**
- **MySQL**
- **PostgreSQL**

### Messaging
- **Apache Kafka** <sub>*(Event-Driven Messaging)*</sub>

### Frontend
- **Angular**

### Observability (Grafana Stack)
- **Prometheus** <sub>*(Metrics)*</sub>
- **Grafana** <sub>*(Dashboards)*</sub>
- **Loki** <sub>*(Log Aggregation)*</sub>
- **Tempo** <sub>*(Distributed Tracing)*</sub>

### Infrastructure
- **Docker**
- **Kubernetes**

### Security
- **OAuth2**
- **Keycloak**

### Testing
- **JUnit**
- **Testcontainers**
- **WireMock**

---

# 🧠 Architecture Concepts

This project demonstrates several distributed system patterns and modern engineering practices:

- **Microservices Architecture**
- **Service Discovery** <sub>*(Dynamic registration and lookup via Eureka)*</sub>
- **API Gateway Pattern**
- **Inter-Service Communication** <sub>*(Synchronous via Feign & Asynchronous via Kafka)*</sub>
- **Centralized Security** <sub>*(Auth integration via Keycloak & OAuth2)*</sub>
- **Event-Driven Architecture** <sub>*(Asynchronous processing using Kafka)*</sub>
- **Resilience Patterns** <sub>*(Fault tolerance via Resilience4j)*</sub>
- **Observability** <sub>*(Centralized logs, metrics, and tracing)*</sub>
- **Database per Service**
- **Centralized API Docs** <sub>*(Gateway-level aggregation)*</sub>
- **Containerization**
- **Integration Testing** <sub>*(Automated tests with WireMock and Testcontainers)*</sub>

---

# 📦 Project Structure

```
springboot-microservices-ecommerce
│
├── api-gateway
├── product-service
├── order-service
├── inventory-service
├── notification-service (planned)
│
├── frontend (planned)
│
├── eureka-server
├── docker
└── kubernetes (planned)
```

🚧Some modules may **not yet exist in the repository** and will be **added as development continues**.

---

# ⚙️ Architecture Flow

**🚧 will be added later**

---

# 🔧 Services

🚧 this section is being actively updated as development continues

## API Gateway

Responsibilities:
- routing requests to backend services 
- centralized security
- centralized api documentation

Technology: 
- Spring Cloud Gateway MVC
- Keycloak (OAuth2, uses PostgreSQL)
- Docker
- Swagger

---
## Eureka Server

Responsibilities:
- service discovery, finds location of services

Technology:
- Spring Cloud Eureka

---
## Product Service

Responsibilities:
- create products  
- list products  

Endpoints:
- POST /api/products createProduct
- GET /api/products getAllProducts

Technology: 
- MongoDB
- Docker
- Swagger

---

## Order Service

Responsibilities:
- create orders

Endpoint:
- POST /api/orders placeOrder

Technology: 
- MySQL
- Docker
- Spring HTTP Interface (for GET /api/inventories) <sub>*(refactored from Feign Client)*</sub>
- Flyway
- Swagger

---

## Inventory Service

Responsibilities:
- check if products are in stock

Endpoint:
- GET /api/inventories isInStock

Technology: 
- MySQL
- Docker
- Flyway
- Swagger

---

## Notification Service(planned)

Responsibilities:
(will be added later)

Technologies:
(will be added later)

---

## Frontend(planned)

Features:
(will be added later)

Technologies:
- Angular
