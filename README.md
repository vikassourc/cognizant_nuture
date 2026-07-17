# 🎓 Cognizant Digital Nurture – Java FSE Program

> **Trainee:** Vikas Srivastav  
> **Program:** Cognizant Digital Nurture – Full Stack Engineering (Java)  
> **Duration:** 5 Weeks  

---

## 📁 Repository Structure

```
cognizant_nuture/
├── Week1/
│   ├── Design_Patterns_and_Principles/
│   │   ├── Exercise1_SingletonPattern/
│   │   │   └── SingletonPatternDemo.java
│   │   └── Exercise2_FactoryMethodPattern/
│   │       └── FactoryMethodPatternDemo.java
│   ├── Algorithms_Data_Structure/
│   │   ├── Exercise2_EcommercePlatformSearch/
│   │   │   └── EcommercePlatformSearch.java
│   │   └── Exercise7_FinancialForecasting/
│   │       └── FinancialForecasting.java
│   └── PLSQL_Exercises/
│       ├── Exercise1_ControlStructures/
│       │   └── ControlStructures.sql
│       └── Exercise3_StoredProcedures/
│           └── StoredProcedures.sql
├── Week2/
│   ├── Spring_Core_Maven/
│   │   ├── Exercise1_ConfiguringBasicSpringApp/
│   │   ├── Exercise2_ImplementingDependencyInjection/
│   │   └── Exercise4_CreatingConfiguringMavenProject/
│   └── Spring_Data_JPA/
│       ├── Exercise1_SpringDataJPA_QuickExample/
│       └── Exercise2_JPA_Hibernate_SpringDataJPA_Differences/
├── Week3/
│   ├── spring-rest-handson/
│   │   ├── Exercise1_SpringWebMaven/          ← Days 25 & 26
│   │   └── Exercise2_RESTfulWebService/       ← Days 27, 28 & 29
│   └── jwt-handson/
│       └── Exercise5_JWTAuthentication/       ← Day 30
└── Week5/
    └── react-handson/
        └── myfirstreact/                      ← ReactJS Part 1 – Exercise 1
```

---

## 🗂️ Week 1 – Mandatory Hands-On Exercises

### 🧩 Design Principles & Patterns

| Exercise | Topic | File |
|----------|-------|------|
| Exercise 1 | Implementing the Singleton Pattern | [SingletonPatternDemo.java](Week1/Design_Patterns_and_Principles/Exercise1_SingletonPattern/SingletonPatternDemo.java) |
| Exercise 2 | Implementing the Factory Method Pattern | [FactoryMethodPatternDemo.java](Week1/Design_Patterns_and_Principles/Exercise2_FactoryMethodPattern/FactoryMethodPatternDemo.java) |

**Key Concepts Covered:**
- Singleton Pattern (thread-safe, double-checked locking)
- Factory Method Pattern (polymorphic object creation)
- SOLID Design Principles
- Open/Closed Principle

---

### 📊 Data Structures & Algorithms

| Exercise | Topic | File |
|----------|-------|------|
| Exercise 2 | E-commerce Platform Search Function | [EcommercePlatformSearch.java](Week1/Algorithms_Data_Structure/Exercise2_EcommercePlatformSearch/EcommercePlatformSearch.java) |
| Exercise 7 | Financial Forecasting | [FinancialForecasting.java](Week1/Algorithms_Data_Structure/Exercise7_FinancialForecasting/FinancialForecasting.java) |

**Key Concepts Covered:**
- Linear Search – O(n) time complexity
- Binary Search – O(log n) time complexity
- Recursion & Memoization (Dynamic Programming)
- Big-O Analysis

---

### 🗃️ PL/SQL Programming

| Exercise | Topic | File |
|----------|-------|------|
| Exercise 1 | Control Structures | [ControlStructures.sql](Week1/PLSQL_Exercises/Exercise1_ControlStructures/ControlStructures.sql) |
| Exercise 3 | Stored Procedures | [StoredProcedures.sql](Week1/PLSQL_Exercises/Exercise3_StoredProcedures/StoredProcedures.sql) |

**Key Concepts Covered:**
- IF-ELSIF-ELSE, CASE statements
- LOOP, WHILE LOOP, FOR LOOP
- Stored Procedures with IN/OUT parameters
- Exception Handling
- Transaction Management

---

## 🗂️ Week 2 – Spring Core & Spring Data JPA

### 🌱 Spring Core (Maven)

| Exercise | Topic |
|----------|-------|
| Exercise 1 | Configuring a Basic Spring Application |
| Exercise 2 | Implementing Dependency Injection |
| Exercise 4 | Creating & Configuring a Maven Project |

**Key Concepts Covered:**
- Spring IoC Container
- Bean Lifecycle & Scopes
- Constructor & Setter Injection
- Maven Project Structure

---

### 🗄️ Spring Data JPA

| Exercise | Topic |
|----------|-------|
| Exercise 1 | Spring Data JPA Quick Example |
| Exercise 2 | JPA vs Hibernate vs Spring Data JPA Differences |

**Key Concepts Covered:**
- JPA Entities & Repositories
- CRUD Operations
- H2 In-Memory Database
- Repository Pattern

---

## 🗂️ Week 3 – Spring REST & JWT Authentication

### 🌐 Spring REST (spring-rest-handson)

#### Exercise 1 – Spring Web Project using Maven (Days 25 & 26)

| Day | Task | Key File |
|-----|------|----------|
| 25 | Create a Spring Web Project using Maven | [pom.xml](Week3/spring-rest-handson/Exercise1_SpringWebMaven/pom.xml) |
| 26 | Spring Core – Load Country from Spring XML Config | [spring-config.xml](Week3/spring-rest-handson/Exercise1_SpringWebMaven/src/main/resources/spring-config.xml) |

**Key Concepts Covered:**
- Spring Boot 3 Maven project setup
- `spring-boot-starter-web` dependency
- Spring XML-based bean configuration (`spring-config.xml`)
- `ClassPathXmlApplicationContext` to load beans
- Country POJO wired via setter injection in XML

#### Exercise 2 – RESTful Web Services (Days 27, 28 & 29)

| Day | Task | Key File |
|-----|------|----------|
| 27 | Hello World RESTful Web Service | [HelloWorldController.java](Week3/spring-rest-handson/Exercise2_RESTfulWebService/src/main/java/com/cognizant/springrest/controller/HelloWorldController.java) |
| 28 | REST – Country Web Service (CRUD) | [CountryController.java](Week3/spring-rest-handson/Exercise2_RESTfulWebService/src/main/java/com/cognizant/springrest/controller/CountryController.java) |
| 29 | REST – Get country based on country code | [CountryController.java](Week3/spring-rest-handson/Exercise2_RESTfulWebService/src/main/java/com/cognizant/springrest/controller/CountryController.java) |

**REST Endpoints:**

| Method | URL | Description |
|--------|-----|-------------|
| GET | `/api/hello` | Hello World response |
| GET | `/api/hello/{name}` | Personalised greeting |
| GET | `/api/greet?name=Vikas&lang=hi` | Multi-language greeting |
| GET | `/api/country/all` | List all countries |
| GET | `/api/country/{code}` | Get country by ISO code (e.g. `IN`, `US`) |
| POST | `/api/country` | Add a new country |
| PUT | `/api/country/{code}` | Update an existing country |
| DELETE | `/api/country/{code}` | Delete a country |

**Key Concepts Covered:**
- `@RestController`, `@RequestMapping`
- `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`
- `@PathVariable`, `@RequestBody`, `@RequestParam`
- `ResponseEntity` with HTTP status codes
- RESTful CRUD operations

---

### 🔐 JWT Authentication (jwt-handson)

#### Exercise 5 – JWT Authentication Service (Day 30)

| File | Purpose |
|------|---------|
| [JwtUtil.java](Week3/jwt-handson/Exercise5_JWTAuthentication/src/main/java/com/cognizant/jwt/util/JwtUtil.java) | Generates & validates JWT tokens (JJWT 0.12) |
| [AuthController.java](Week3/jwt-handson/Exercise5_JWTAuthentication/src/main/java/com/cognizant/jwt/controller/AuthController.java) | POST `/auth/login` – returns signed JWT |
| [JwtAuthenticationFilter.java](Week3/jwt-handson/Exercise5_JWTAuthentication/src/main/java/com/cognizant/jwt/filter/JwtAuthenticationFilter.java) | Intercepts requests and validates Bearer token |
| [SecurityConfig.java](Week3/jwt-handson/Exercise5_JWTAuthentication/src/main/java/com/cognizant/jwt/config/SecurityConfig.java) | Spring Security chain – STATELESS, CSRF disabled |
| [SecureController.java](Week3/jwt-handson/Exercise5_JWTAuthentication/src/main/java/com/cognizant/jwt/controller/SecureController.java) | Protected endpoints requiring valid JWT |

**JWT Endpoints:**

| Method | URL | Auth Required | Description |
|--------|-----|:---:|-------------|
| POST | `/auth/login` | ❌ | Login – returns signed JWT |
| GET | `/auth/validate` | ✅ | Validates JWT and returns metadata |
| GET | `/api/secure/hello` | ✅ | Protected greeting |
| GET | `/api/secure/profile` | ✅ | Returns authenticated user profile |
| GET | `/api/secure/dashboard` | ✅ | Protected dashboard |

**Test Credentials:**
| Username | Password | Role |
|----------|----------|------|
| `admin` | `admin123` | ROLE_ADMIN |
| `user` | `user123` | ROLE_USER |
| `vikas` | `password` | ROLE_USER |

**Key Concepts Covered:**
- JSON Web Token (JWT) structure: Header.Payload.Signature
- HMAC-SHA256 signing with JJWT 0.12
- Spring Security filter chain customisation
- `OncePerRequestFilter` for JWT validation
- Stateless REST API (no server-side sessions)
- `AuthenticationManager` for credential validation

---

## 🛠️ How to Run

### Spring REST Service (Week 3 – Exercises 1 & 2)
```bash
cd Week3/spring-rest-handson/Exercise1_SpringWebMaven
mvn spring-boot:run
# Server starts on: http://localhost:8080
# Test: curl http://localhost:8080/api/hello
# Test: curl http://localhost:8080/api/country/IN
```

### JWT Authentication Service (Week 3 – Exercise 5)
```bash
cd Week3/jwt-handson/Exercise5_JWTAuthentication
mvn spring-boot:run
# Server starts on: http://localhost:8081

# Step 1 – Login and get JWT
curl -X POST http://localhost:8081/auth/login \
     -H "Content-Type: application/json" \
     -d '{"username":"admin","password":"admin123"}'

# Step 2 – Use JWT to access protected endpoint
curl http://localhost:8081/api/secure/hello \
     -H "Authorization: Bearer <token>"
```

### Java Programs (Week 1)
```bash
# Compile and run any Java exercise
cd Week1/Design_Patterns_and_Principles/Exercise1_SingletonPattern
javac SingletonPatternDemo.java
java SingletonPatternDemo
```

### PL/SQL Scripts (Week 1)
```sql
-- Connect to Oracle DB and run:
@Week1/PLSQL_Exercises/Exercise1_ControlStructures/ControlStructures.sql
```

---

## 🗂️ Week 4 – Microservices with Spring Boot 3 & Spring Cloud

### 🏗️ Architecture

```
Client
  │
  ▼
┌─────────────────────────────────────────┐
│  API Gateway  (port 8080)               │
│  Spring Cloud Gateway + Load Balancer   │
└──────────────┬──────────────────────────┘
               │  Service discovery via Eureka
     ┌─────────┴──────────┐
     ▼                    ▼
┌──────────────┐    ┌──────────────┐
│Account Svc   │    │  Loan Svc    │
│ (port 8081)  │    │ (port 8082)  │
│  H2 + JPA    │    │  H2 + JPA    │
└──────────────┘    └──────────────┘
          │                │
          └────────┬───────┘
                   ▼
        ┌──────────────────────┐
        │  Eureka Server       │
        │  (port 8761)         │
        │  Service Registry    │
        └──────────────────────┘
```

### 📦 Microservices (Exercise 2)

| Service | Port | Key File | Purpose |
|---------|------|----------|---------|
| **eureka-server** | 8761 | [EurekaServerApplication.java](Week4/microservices-handson/eureka-server/src/main/java/com/cognizant/eurekaserver/EurekaServerApplication.java) | Service registry & discovery |
| **account-service** | 8081 | [AccountController.java](Week4/microservices-handson/account-service/src/main/java/com/cognizant/accountservice/controller/AccountController.java) | Bank account CRUD microservice |
| **loan-service** | 8082 | [LoanController.java](Week4/microservices-handson/loan-service/src/main/java/com/cognizant/loanservice/controller/LoanController.java) | Loan management microservice |
| **api-gateway** | 8080 | [application.yml](Week4/microservices-handson/api-gateway/src/main/resources/application.yml) | Single entry point, routes + LB |

### 🌐 API Gateway Routes

| Gateway URL | Routes To | Description |
|-------------|-----------|-------------|
| `GET  /account-service/api/accounts` | account-service | All accounts |
| `GET  /account-service/api/accounts/{id}` | account-service | Account by ID |
| `GET  /account-service/api/accounts/customer/{id}` | account-service | Accounts by customer |
| `POST /account-service/api/accounts` | account-service | Create account |
| `GET  /loan-service/api/loans` | loan-service | All loans |
| `GET  /loan-service/api/loans/customer/{id}` | loan-service | Loans by customer |
| `POST /loan-service/api/loans` | loan-service | Apply for loan |
| `PATCH /loan-service/api/loans/{id}/status` | loan-service | Update loan status |

**Key Concepts Covered:**
- `@EnableEurekaServer` – Eureka service registry
- `@EnableDiscoveryClient` – microservice registration with Eureka
- Spring Cloud Gateway routing with `lb://` (load-balanced URIs)
- `StripPrefix` filter to remove gateway prefix before forwarding
- `GlobalFilter` – custom request/response logging filter
- Spring Data JPA with H2 in-memory DB per microservice
- Service-level isolation (separate DB per service)

### 🛠️ How to Run (Week 4)
```bash
# Step 1 – Start Eureka Server FIRST
cd Week4/microservices-handson/eureka-server
mvn spring-boot:run
# Dashboard: http://localhost:8761

# Step 2 – Start Account Service
cd Week4/microservices-handson/account-service
mvn spring-boot:run

# Step 3 – Start Loan Service
cd Week4/microservices-handson/loan-service
mvn spring-boot:run

# Step 4 – Start API Gateway
cd Week4/microservices-handson/api-gateway
mvn spring-boot:run

# Test via Gateway:
curl http://localhost:8080/account-service/api/accounts
curl http://localhost:8080/loan-service/api/loans
curl http://localhost:8080/loan-service/api/loans/customer/1
```

---

## 🗂️ Week 5 – ReactJS Part 1

### ⚛️ React Hands-On (react-handson)

#### Exercise 1 – Create Your First React App (myfirstreact)

**Objectives:**
- Define SPA (Single-Page Application) and its benefits
- Understand how React works and its Virtual DOM
- Identify differences between SPA and MPA
- Explain Pros & Cons of Single-Page Applications
- Set up a React development environment
- Use `create-react-app` to scaffold a project

| File | Purpose |
|------|---------|
| [App.js](Week5/react-handson/myfirstreact/src/App.js) | Main React component – renders welcome heading |
| [index.js](Week5/react-handson/myfirstreact/src/index.js) | React entry point – mounts App to DOM |
| [App.css](Week5/react-handson/myfirstreact/src/App.css) | Component-level styles |

**Key Concepts Covered:**
- `create-react-app` project scaffolding
- JSX syntax and React components
- Single-Page Application (SPA) architecture
- Virtual DOM concept
- React's unidirectional data flow
- `npm start` development server

### 🛠️ How to Run (Week 5)
```bash
# Navigate to the myfirstreact app
cd Week5/react-handson/myfirstreact

# Install dependencies (if not already installed)
npm install

# Start the development server
npm start
# App runs on: http://localhost:3000
# Displays: "Welcome to the first session of React"
```

---

## 📌 Program Details

- **Skills Covered:** Design Patterns, Data Structures & Algorithms, PL/SQL, Spring Core, Spring Data JPA, Spring REST, JWT Security, Microservices, Spring Cloud, ReactJS
- **Language:** Java 17 (Spring Boot 3 + Spring Cloud 2023), Oracle PL/SQL, JavaScript (React 18)
- **Paradigms:** OOP, REST, IoC, Stateless Authentication, Microservices Architecture, SPA (Single-Page Application)

---

*Solutions submitted as part of the Cognizant Digital Nurture Java FSE program.*
