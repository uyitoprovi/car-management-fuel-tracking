# 🚗 Car Management & Fuel Tracking System

## 📌 Assignment Overview

This project is a Java-based Car Management and Fuel Tracking System developed as part of a technical assignment.

### 🎯 The goal of the assignment is to demonstrate:

- REST API development with Spring Boot
- In-memory data management (no database)
- Manual Java Servlet integration
- A Java CLI application communicating with the backend via HTTP
- Clean architecture and service reuse

---

## 🧩 Project Architecture

The system is divided into two main modules:

- **Backend Server**
    - Spring Boot REST API
    - In-memory storage using Java collections
    - Manual Java Servlet integration

- **CLI Client**
    - Standalone Java application
    - Communicates with the backend using `HttpClient`

---

## 📦 Project Structure
    car-fuel-tracker/
├── car-fuel-backend/          # Spring Boot backend
│   ├── src/main/java/com/codehills/

│   │   ├── model/             # Car, FuelEntry, FuelStats
│   │   ├── service/           # CarService
│   │   ├── controller/        # REST controllers
│   │   └── servlet/           # Manual FuelStatsServlet
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml
│
├── car-fuel-cli/              # Java CLI client
│   ├── src/main/java/com/codehills/cli/
│   │   └── Main.java
│   └── pom.xml
│
└── README.md

---

## 🔧 Backend Server (`car-fuel-backend`)

### ✅ Technologies

- Java 17
- Spring Boot
- Maven
- In-memory storage (Lists / Maps)

### ✅ Features Implemented

- Car registration
- Fuel entry tracking
- Fuel consumption statistics
- REST API endpoints
- Manual Java Servlet integration
- Proper HTTP status codes and error handling

---

### 🔌 REST API Endpoints

| Action       | Method | Endpoint                          | Description               |
|--------------|--------|-----------------------------------|---------------------------|
| Create car   | POST   | `/api/cars`                       | Register a new car        |
| List cars    | GET    | `/api/cars`                       | Retrieve all cars         |
| Add fuel     | POST   | `/api/cars/{id}/fuel`             | Add fuel entry            |
| Fuel stats   | GET    | `/api/cars/{id}/fuel/stats`       | Get fuel statistics       |

### 📊 Fuel Statistics Returned

    - Total fuel consumed
    - Total fuel cost
    - Average fuel consumption (L/100km)

---

### 🧪 Java Servlet Integration

A manual Java Servlet is implemented to demonstrate understanding of the servlet lifecycle.

**Servlet Details:**

    - **Endpoint**: `GET /servlet/fuel-stats?carId={id}`
    - Extends `HttpServlet`
    - Overrides `doGet()`
    - Manually parses query parameters
    - Sets response status codes and `Content-Type`
    - Returns JSON output
    - Reuses the same service layer as the REST API

---

### ▶️ Running the Backend

#### Requirements

    - Java 17+
    - Maven

#### Run the Backend

    cd car-fuel-backend
    mvn spring-boot:run  # on linux
    mvnw.cmd spring-boot:run # on window

    Backend runs at:
        http://localhost:8080

📬 Using Postman to Test the API

1. Create a Car
    - Method: POST
    - URL: http://localhost:8080/api/cars
    - Body (JSON):
    {
        "brand": "Toyota",
        "model": "Corolla",
        "year": 2020
    }

2. Add Fuel Entry

    - Method: POST
    - URL: http://localhost:8080/api/cars/1/fuel
    - Body (JSON):
    {
        "liters": 40,
        "price": 50,
        "odometer": 10000
    }


3. Get Fuel Stats

    - Method: GET

    - URL: http://localhost:8080/api/cars/1/fuel/stats

### 🌍 View Data in Browser
- All Cars:

    http://localhost:8080/api/cars

- Fuel Stats for Car 1:

    http://localhost:8080/api/cars/1/fuel/stats

### 💻 CLI Application (car-fuel-cli)

A standalone Java CLI application that communicates with the backend using:

    - java.net.http.HttpClient
    - HTTP requests and JSON responses

## ✅ Requirements

    - Java 17+
    - Maven

## ▶️ Build the CLI

    cd car-fuel-cli
    mvn clean package


This generates a runnable JAR in the target/ directory.

## ▶️ Run the CLI

    java -jar target/shaded/car-fuel-cli-0.0.1-SNAPSHOT-shaded.jar



### 🧪 CLI Commands

## 1️⃣ Create Car

    java -jar target/shaded/car-fuel-cli-0.0.1-SNAPSHOT-shaded.jar create-car --brand Toyota --model Corolla --year 2020


## 2️⃣ Add Fuel Entry

    > java -jar target/shaded/car-fuel-cli-0.0.1-SNAPSHOT-shaded.jar add-fuel --carId 1 --liters 40 --price 52.5 --odometer 45000
    > java -jar target/shaded/car-fuel-cli-0.0.1-SNAPSHOT-shaded.jar add-fuel --carId 1 --liters 20 --price 22.5 --odometer 10000

## 3️⃣ View Fuel Statistics

    java -jar target/shaded/car-fuel-cli-0.0.1-SNAPSHOT-shaded.jar fuel-stats --carId 1


## ✅ Expected Output
Total fuel: 100 L
Total cost: 127.50
Average consumption: 0.0 L/100km

### Output for Car, fuel entry and fuel stats
    [
    {
        "id": 1,
        "brand": "Toyota",
        "model": "Corolla",
        "year": 2020,
        "fuelEntries": [
        {
            "liters": 40,
            "price": 52.5,
            "odometer": 45000
        },
        {
            "liters": 40,
            "price": 52.5,
            "odometer": 45000
        },
        {
            "liters": 20,
            "price": 22.5,
            "odometer": 10000
        }
        ]
    }
    ]
---
### 🧪 Testing

## ✅ Backend Tests

    cd car-fuel-backend
    mvn test

Includes:
- Unit tests for fuel statistics calculations
- Service-layer validation logic
## ✅ CLI Tests

    cd car-fuel-cli

    mvn test


Includes:
    - JSON parsing tests
    - Output formatting validation

### 📌 Assumptions & Constraints
    - Data is stored in memory only
    - No database or authentication is used
    - Odometer readings must increase
    - At least two fuel entries are required to calculate statistics
    - Invalid car IDs return proper HTTP errors (e.g., 404)

### 📚 Technologies Used
    - Java 17
    - Spring Boot
    - Maven
    - Java Servlet API
    - JUnit 5

👨‍💻 Author
Providence Uyitonnyeho
GitHub: https://github.com/uyitoprovi

