# 🎓 Cognizant Digital Nurture – Java FSE Program

> **Trainee:** Vikas Srivastav
> **Program:** Cognizant Digital Nurture – Full Stack Engineering (Java)
> **Duration:** 6 Weeks

---

## 📁 Repository Structure

```
cognizant_nuture/
├── Week1/
│   ├── Design_Patterns_and_Principles/
│   │   ├── Exercise1_SingletonPattern/
│   │   └── Exercise2_FactoryMethodPattern/
│   ├── Algorithms_Data_Structure/
│   │   ├── Exercise2_EcommercePlatformSearch/
│   │   └── Exercise7_FinancialForecasting/
│   └── PLSQL_Exercises/
│       ├── Exercise1_ControlStructures/
│       └── Exercise3_StoredProcedures/
├── Week2/
│   ├── Spring_Core_Maven/
│   └── Spring_Data_JPA/
├── Week3/
│   ├── spring-rest-handson/
│   └── jwt-handson/
├── Week4/
│   └── microservices-handson/
├── Week5/
│   └── react-handson/
│       ├── myfirstreact/           ← Exercise 1: First React App
│       ├── StudentApp/             ← Exercise 2: Class Components
│       ├── scorecalculatorapp/     ← Exercise 3: Props & Functional Components
│       ├── blogapp/                ← Assignment 4: Lifecycle Hooks
│       └── cohortapp/              ← Assignment 5: CSS Modules
└── Week6/
    └── react-handson/
        ├── cricketapp/             ← Assignment 9: ES6 Features
        ├── officespacerentalapp/   ← JSX Elements & Attributes
        ├── eventexamplesapp/       ← Event Handling
        ├── ticketbookingapp/       ← Conditional Rendering (Login/Logout)
        └── bloggerapp/             ← 6 Conditional Rendering Methods
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

**Key Concepts Covered:**
- JSON Web Token (JWT) structure: Header.Payload.Signature
- HMAC-SHA256 signing with JJWT 0.12
- Spring Security filter chain customisation
- `OncePerRequestFilter` for JWT validation
- Stateless REST API (no server-side sessions)

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

### 📦 Microservices

| Service | Port | Purpose |
|---------|------|---------|
| **eureka-server** | 8761 | Service registry & discovery |
| **account-service** | 8081 | Bank account CRUD microservice |
| **loan-service** | 8082 | Loan management microservice |
| **api-gateway** | 8080 | Single entry point, routes + LB |

**Key Concepts Covered:**
- `@EnableEurekaServer` – Eureka service registry
- `@EnableDiscoveryClient` – microservice registration
- Spring Cloud Gateway routing with `lb://` load-balanced URIs
- `StripPrefix` filter for gateway prefix removal
- `GlobalFilter` – custom request/response logging
- Service-level isolation (separate DB per service)

---

## 🗂️ Week 5 – ReactJS (Part 1)

### ⚛️ React Hands-On Apps

#### 🔹 Exercise 1 – `myfirstreact` — First React App
- Set up React environment using `create-react-app`
- JSX syntax, components, Virtual DOM concept
- `npm start` development server

#### 🔹 Exercise 2 – `StudentApp` — Class Components
- Class-based components with state
- Home, About, Contact component pages
- Props passing between components

#### 🔹 Exercise 3 – `scorecalculatorapp` — Props & Functional Components
- Functional components with props
- `CalculateScore` component
- Component-level CSS styling

#### 🔹 Assignment 4 – `blogapp` — Component Lifecycle Hooks

| File | Purpose |
|------|---------|
| [Post.js](Week5/react-handson/blogapp/src/Post.js) | Functional component — displays single blog post |
| [Posts.js](Week5/react-handson/blogapp/src/Posts.js) | Class component with lifecycle hooks |

**Lifecycle Hooks Implemented:**
- `componentDidMount()` — fetches posts from JSONPlaceholder API after mount
- `componentDidCatch(error, info)` — Error Boundary catches child errors

**Lifecycle Sequence:** Constructor → render() → componentDidMount() → setState → render()

#### 🔹 Assignment 5 – `cohortapp` — CSS Modules

| File | Purpose |
|------|---------|
| [CohortDetails.module.css](Week5/react-handson/cohortapp/src/CohortDetails.module.css) | CSS Module with `.box` class + `dt` tag selector |
| [CohortDetails.js](Week5/react-handson/cohortapp/src/CohortDetails.js) | Class component — imports & applies CSS Module |

**CSS Module Features:**
- `.box` — width:300px, inline-block, margin:10px, padding:10px 20px, 1px solid border, border-radius:10px
- `dt` tag selector — font-weight: 500
- Inline style: `<h3>` color green for `Ongoing`, blue for all other statuses

### 🛠️ How to Run (Week 5)
```bash
cd Week5/react-handson/<app-name>
npm install
npm start
# App runs on: http://localhost:3000
```

---

## 🗂️ Week 6 – ReactJS (Part 2) – ES6, Events & Conditional Rendering

### ⚛️ React Apps

#### 🔹 Assignment 9 – `cricketapp` — ES6 Features in React

| Component | File | ES6 Feature |
|-----------|------|-------------|
| ListofPlayers | [ListofPlayers.js](Week6/react-handson/cricketapp/src/ListofPlayers.js) | `map()` to display 11 players, `filter()` arrow function for scores ≤ 70 |
| IndianPlayers | [IndianPlayers.js](Week6/react-handson/cricketapp/src/IndianPlayers.js) | Array destructuring for Odd/Even players, Spread `[...arr1, ...arr2]` to merge arrays |
| App | [App.js](Week6/react-handson/cricketapp/src/App.js) | Simple `if/else` flag variable — `flag=true` → ListofPlayers, `flag=false` → IndianPlayers |

**ES6 Concepts Covered:**
- `map()` — render list of players
- Arrow function `filter()` — filter players with score ≤ 70
- Array **Destructuring** — `const [first, second, ...] = players`
- **Spread / Merge** — `[...T20players, ...RanjiTrophyPlayers]`
- `if/else` flag-based conditional rendering

---

#### 🔹 `officespacerentalapp` — JSX Elements, Attributes & Objects

| Concept | Implementation |
|---------|---------------|
| JSX Element | `<h1>Office Space, at Affordable Range</h1>` |
| JSX Attribute | `<img src={officeImg} alt="Office Space" />` |
| Object | Single office object `{ name, rent, address }` |
| List + Loop | `officeList.map()` renders 5 office cards |
| Conditional CSS | Rent color **red** if `< 60000`, **green** if `> 60000` |

---

#### 🔹 `eventexamplesapp` — React Event Handling

| Button | Event Handler | Behaviour |
|--------|--------------|-----------|
| **Increment** | `handleIncrement()` | Calls **2 methods**: `increment()` + `sayHello()` → alert "Hello: Member!" |
| **Decrement** | `decrement()` | Decreases counter state |
| **Say welcome** | `sayWelcome('welcome')` | Passes argument to function → alert "welcome" |
| **Click on me** | `handleClick(e)` | Synthetic event `onClick` → alert "I was clicked" |

**CurrencyConvertor Component:**
- Controlled form with `onChange` → `handleChange`
- `onSubmit` → `handleSubmit` converts INR to Euro (amount × 80)
- Alert: `"Converting to: Euro Amount is 6400"`

**Key Concepts Covered:**
- Multiple methods invoked from single event handler
- Passing arguments via arrow functions in JSX
- Synthetic events in React
- Controlled form components (`value` + `onChange`)
- `handleSubmit` with `e.preventDefault()`

---

#### 🔹 `ticketbookingapp` — Conditional Rendering (Login/Logout)

| State | Component Shown | Description |
|-------|----------------|-------------|
| `isLoggedIn = false` | [GuestPage.js](Week6/react-handson/ticketbookingapp/src/GuestPage.js) | "Please sign up." + Login button + Flight details table |
| `isLoggedIn = true` | [UserPage.js](Week6/react-handson/ticketbookingapp/src/UserPage.js) | "Welcome back" + Logout button + Ticket booking form |

**Logic in App.js:**
```js
if (isLoggedIn) {
  page = <UserPage onLogout={this.handleLogout} />;
} else {
  page = <GuestPage onLogin={this.handleLogin} />;
}
```

---

#### 🔹 `bloggerapp` — 6 Conditional Rendering Methods

| # | Method | Component | Description |
|---|--------|-----------|-------------|
| 1 | **`if / else`** | [CourseDetails.js](Week6/react-handson/bloggerapp/src/CourseDetails.js) | Skip inactive courses unless `showAll` flag is true |
| 2 | **Ternary `? :`** | [BlogDetails.js](Week6/react-handson/bloggerapp/src/BlogDetails.js) | Show/hide blogs, style drafts, fallback author name |
| 3 | **`&&` Short-Circuit** | [BookDetails.js](Week6/react-handson/bloggerapp/src/BookDetails.js) | Show price & "Out of Stock" badge only when applicable |
| 4 | **`switch` statement** | [App.js](Week6/react-handson/bloggerapp/src/App.js) | Tab navigation — All / Courses / Books / Blogs |
| 5 | **Element Variable** | [CourseDetails.js](Week6/react-handson/bloggerapp/src/CourseDetails.js) | Store JSX in `let heading` variable before render |
| 6 | **IIFE** `(()=>{})()` | [BookDetails.js](Week6/react-handson/bloggerapp/src/BookDetails.js) | Inline self-invoking function inside JSX |

**3 Components:**
- `CourseDetails` — Angular, React courses with date
- `BookDetails` — Master React, Deep Dive Angular, Mongo Essentials with price
- `BlogDetails` — React Learning, Installation blog posts with author & body

### 🛠️ How to Run (Week 6)
```bash
cd Week6/react-handson/<app-name>
npm install
npm start
# App runs on: http://localhost:3000
```

---

## 🛠️ How to Run – Spring Apps

### Spring REST Service (Week 3)
```bash
cd Week3/spring-rest-handson/Exercise2_RESTfulWebService
mvn spring-boot:run
# Server: http://localhost:8080
# Test: curl http://localhost:8080/api/country/IN
```

### JWT Authentication (Week 3)
```bash
cd Week3/jwt-handson/Exercise5_JWTAuthentication
mvn spring-boot:run
# Step 1 – Login
curl -X POST http://localhost:8081/auth/login \
     -H "Content-Type: application/json" \
     -d '{"username":"admin","password":"admin123"}'
# Step 2 – Use JWT
curl http://localhost:8081/api/secure/hello \
     -H "Authorization: Bearer <token>"
```

### Microservices (Week 4)
```bash
# Start in order:
cd Week4/microservices-handson/eureka-server && mvn spring-boot:run   # port 8761
cd Week4/microservices-handson/account-service && mvn spring-boot:run # port 8081
cd Week4/microservices-handson/loan-service && mvn spring-boot:run    # port 8082
cd Week4/microservices-handson/api-gateway && mvn spring-boot:run     # port 8080
# Test: curl http://localhost:8080/account-service/api/accounts
```

---

## 📌 Program Details

| Item | Detail |
|------|--------|
| **Skills Covered** | Design Patterns, DSA, PL/SQL, Spring Core, Spring Data JPA, Spring REST, JWT, Microservices, Spring Cloud, ReactJS |
| **Languages** | Java 17, Oracle PL/SQL, JavaScript (ES6+), JSX |
| **Frameworks** | Spring Boot 3, Spring Cloud 2023, React 18 |
| **Paradigms** | OOP, REST, IoC, Stateless Auth, Microservices, SPA |
| **Tools** | Maven, npm, create-react-app, H2 DB, Eureka, JWT |

---

*Solutions submitted as part of the Cognizant Digital Nurture Java FSE program.*
