# springboot-microservices-ecommerce
# 🚧 Work in Progress

This project is currently **under active development**. Not everything down there is implemented yet.  

---

# 🧩 Spring Boot Microservices Architecture Practice

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
- **Spring Boot 3**            (Spring Web, Spring Data JPA, Lombok)
- **Spring Cloud Gateway MVC** (API Gateway)
- **Spring Cloud Eureka**      (Service Discovery)
- **Spring Cloud OpenFeign**   (REST Client)
- **Resilience4j**             (Circuit Breaker & Retries)
- **Flyway**                   (Database Migrations)
- **Swagger (OpenAPI) v3**     (API Documentation)

### Databases
- **MongoDB**
- **MySQL**
- **PostgreSQL**

### Messaging
- **Apache Kafka** (Event-Driven Messaging)

### Frontend
- **Angular**

### Observability (Grafana Stack)
- **Prometheus**   (Metrics)
- **Grafana**      (Dashboards)
- **Loki**         (Log Aggregation)
- **Tempo**        (Distributed Tracing)

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

- **Microservices Architecture**---------(Decoupled, specialized services)
- **Service Discovery**------------------(Dynamic service registration and lookup)
- **API Gateway Pattern**----------------(Edge routing, filtering, and centralized security)
- **Inter-Service Communication**--------(Synchronous via Feign, Asynchronous via Kafka)
- **Centralized Security**---------------(Identity & Access Management using Keycloak)
- **Event-Driven Architecture**----------(Decoupling services using message brokers)
- **Resilience Patterns**----------------(Circuit Breaker, Retries to handle partial failures)
- **Observability**----------------------(Centralized logging, distributed tracing, and metrics)
- **Database per Service**---------------(Ensuring data isolation and autonomy)
- **Centralized API Documentation**------(Aggregated OpenAPI documentation for all services)
- **Containerization & Orchestration**---(Packaged deployments with Docker and K8s)
- **Integration Testing**----------------(Automated tests using real containers and downstream mocks)

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

Some modules may **not yet exist in the repository** and will be **added as development continues**.

---

# ⚙️ Architecture Flow

**will be added later**

---

# 🔧 Services

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
- Feign Client (for GET /api/inventories)
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
