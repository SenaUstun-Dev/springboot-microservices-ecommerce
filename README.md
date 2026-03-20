# springboot-microservices-ecommerce
# 🚧 Work in Progress

This project is currently **under active development**.  

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
The main purpose of this project is to **gain hands-on experience with microservices architecture** and everthing in the Tech Stack section.

Key goals of this project include:

- designing **independent microservices**
- implementing **service-to-service communication**
- building an **API Gateway**
- applying **resilience patterns**
- practicing **event-driven architecture**
- learning **containerized deployments**
- having **hands-on experience with everything in tech stack**

This project is intended for **learning, experimentation, and portfolio demonstration**.

---

# 🛠 Tech Stack

### Backend
- **Java**
- **Spring Boot** (Spring Web, Spring Data JPA, Lombok)
- **Spring Cloud Gateway** (API Gateway)
- **Resilience4j**
- **Spring Cloud OpenFeign**

### Databases
- **MongoDB**
- **MySQL**

### Messaging
- **Apache Kafka**

### Frontend
- **Angular**

### Observability
- **Prometheus**
- **Grafana**
- **Loki**
- **Tempo**

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

This project demonstrates several distributed system patterns:

- **Microservices Architecture**
- **API Gateway Pattern**
- **Event-Driven Architecture**
- **Circuit Breaker Pattern**
- **Service Isolation**

---

# 📦 Project Structure

```
springboot-microservices-ecommerce
│
├── api-gateway (currently in progress)
├── product-service (done)
├── order-service (done)
├── inventory-service (done)
├── notification-service (planned)
│
├── frontend (planned)
│
├── docker (planned)
└── kubernetes (planned)
```

Some modules may **not yet exist in the repository** and will be **added as development continues**.

---

# ⚙️ Architecture Flow

**will be added later**

---

# 🔧 Services

## API Gateway(planned)

Responsibilities:
- routing requests to backend services and resilience handling.

Technology: 
- Spring Cloud Gateway

---

## Product Service

Responsibilities:
- create products  
- list products  

Endpoints:
- POST /api/products
- GET /api/products

Technology:
- MongoDB

---

## Order Service(planned)

Responsibilities:
- create orders

Endpoints:
- POST /api/orders

Technology:
- MySQL
---

## Inventory Service(planned)

Responsibilities:
- Checks if the product is in stock

Endpoints:
- GET /api/inventories

Technology:
- MySQL

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
