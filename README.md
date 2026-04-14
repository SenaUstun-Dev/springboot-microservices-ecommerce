# springboot-microservices-ecommerce
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

> **[NOTE]** check out **refactor/feign-to-http-interface** branch to see the refactored code
> its a more modern and recommended way to handle inter-service communication in Spring Boot


### Backend
- **Java 21**
- **Spring Boot 4** <sub>*(Spring Web, Spring Data JPA, Lombok)*</sub>
- **Spring Cloud Gateway MVC** <sub>*(API Gateway)*</sub>
- **Spring Cloud Eureka** <sub>*(Service Discovery)*</sub>
- **Spring Cloud OpenFeign** <sub>*(REST Client)*</sub>  (refactored in branch **refactor/feign-to-http-interface**)
- **Resilience4j** <sub>*(Circuit Breaker & Retries)*</sub>
- **Flyway** <sub>*(Database Migrations)*</sub>
- **Swagger (OpenAPI) v3** <sub>*(API Documentation)*</sub>

### Databases
- **MongoDB**
- **MySQL**
- **PostgreSQL**

### Messaging
- **Apache Kafka (Kraft)** <sub>*(Event-Driven Messaging)*</sub>

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
- **Keycloak** (uses PostgreSQL)

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
├── notification-service
│
├── frontend (might or might not be added later)
│
├── eureka-server
├── docker
└── k8s 
```
---

# ⚙️ Architecture Flow

**🚧 will be added later**

---

# 🔧 Services
## API Gateway

Responsibilities:
- routing requests to backend services 
- centralized security
- centralized api documentation

Technology: 
- Spring Cloud Gateway MVC
- Keycloak
- Docker
- Swagger
- Grafana Stack (Prometheus, Grafana, Loki, Tempo)

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
- Grafana Stack (Prometheus, Grafana, Loki, Tempo)

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
- Kafka
- Grafana Stack (Prometheus, Grafana, Loki, Tempo)

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
- Grafana Stack (Prometheus, Grafana, Loki, Tempo)

---

## Notification Service

Responsibilities:
-send notifications to users (everytime when order is placed)

Technologies:
- Kafka
- Spring Email
- Grafana Stack (Prometheus, Grafana, Loki, Tempo)

---

## Frontend(might or might not be added later)

Technologies:
- Angular
