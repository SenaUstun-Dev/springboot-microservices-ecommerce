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

This repository contains a **microservices architecture practice project** built with **Spring Boot, Kafka, Angular, Docker, and Kubernetes**.

The project simulates a simplified **e-commerce platform** where each business domain is implemented as an independent microservice. Services communicate through both **REST APIs (synchronous)** and **Kafka events (asynchronous)**.

The main purpose of this project is to **gain hands-on experience with microservices architecture** and modern backend infrastructure.

Key goals of this project include:

- designing **independent microservices**
- implementing **service-to-service communication**
- building an **API Gateway**
- applying **resilience patterns**
- practicing **event-driven architecture**
- learning **containerized deployments**
- having **hands-on experience with everything in tech stack**

This project is intended for **learning, experimentation, and portfolio demonstration**.

## 🏗 System Architecture

```mermaid
flowchart LR

Actor((Actor))

subgraph System["Microservices Architecture"]

    Gateway["API Gateway (Resilience4J)"]
    Auth["Auth Server"]

    subgraph Services["Internal Services"]

        Product["Product Service"]
        ProductDB[(MongoDB)]

        Order["Order Service"]
        OrderDB[(MySQL)]

        Inventory["Inventory Service"]
        InventoryDB[(MySQL)]

        Notification["Notification Service"]

        Kafka{{Kafka}}

    end

    Gateway --> Auth

    Gateway --> Product
    Gateway --> Order

    Product --> ProductDB
    Order --> OrderDB
    Inventory --> InventoryDB

    Order -->|Sync Communication (Resilience4J)| Inventory
    Order -.->|Async Communication| Kafka
    Kafka -.-> Notification

end

Actor --> Gateway
```

---

# 🛠 Tech Stack

## Backend
- **Java**
- **Spring Boot**
- Spring Web
- Spring Data JPA
- Spring Cloud Gateway
- Resilience4j
- Spring Kafka
- Lombok

## Frontend
- **Angular**

## Messaging
- **Apache Kafka**

## Infrastructure
- **Docker**
- **Kubernetes**

## Security (planned)
- OAuth2 / Keycloak

## Build Tool
- **Maven**

## Testing (planned)
- JUnit
- Testcontainers

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
├── api-gateway (planned)
├── product-service (currently in progress)
├── order-service (planned)
├── inventory-service (planned)
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

General request flow of the system:

```
Client (Angular)
        │
        ▼
API Gateway
        │
        ▼
Microservices
│        │        │        │
Product  Order  Inventory  Notification
Service  Service  Service   Service
        │
        ▼
Kafka (Event Messaging)
```

Example order workflow:

1. User places an order from the **frontend**
2. Request reaches the **API Gateway**
3. Gateway routes the request to **Order Service**
4. Order Service checks stock via **Inventory Service**
5. If successful, an **order event is published to Kafka**
6. **Notification Service** listens to the event and sends a notification

---

# 🔧 Services

## API Gateway

Acts as the **single entry point** for all client requests.

Responsibilities:

- routing requests to backend services  
- centralized access point  
- resilience handling (circuit breaker / fallback)

Technology:

- Spring Cloud Gateway

Status: **Planned**

---

## Product Service

Manages the **product catalog**.

Responsibilities:

- create products  
- list products  
- manage product information  

Example endpoints:

```
POST /api/product
GET /api/product
```

Status: **Planned**

---

## Order Service

Handles **order creation and order workflows**.

Responsibilities:

- accept order requests  
- validate inventory availability  
- publish order events to Kafka  

Example endpoint:

```
POST /api/order
```

Status: **Planned**

---

## Inventory Service

Responsible for **inventory management**.

Responsibilities:

- track product stock  
- validate product availability for orders  

Example endpoint:

```
GET /api/inventory
```

Status: **Planned**

---

## Notification Service

Handles **event-based notifications**.

Responsibilities:

- listen to Kafka events  
- send notifications when an order is placed  

Technologies:

- Spring Kafka  
- JavaMailSender  

Status: **Planned**

---

## Frontend

Angular application providing the **user interface** for the system.

Possible features:

- product listing  
- product creation  
- order placement  
- shared UI components  

Status: **Planned**

---

The system is designed to run in a **containerized environment**.

## Docker

Each service will run in its own container.

---

## Kubernetes

Kubernetes manifests will be used to deploy:

- microservices  
- infrastructure components  
- messaging systems  

Status: **Planned**
