package com.cognizant.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Week 3 – Exercise 5 (Day 30)
 * Task: Create authentication service that returns JWT
 *
 * Main entry point for the JWT Authentication Service.
 *
 * Authentication Flow:
 *   1. POST /auth/login  { "username": "admin", "password": "password" }
 *   2. Server validates credentials and returns a signed JWT
 *   3. Client includes the JWT in subsequent requests:
 *      Authorization: Bearer <token>
 *   4. Server validates JWT on every protected endpoint
 */
@SpringBootApplication
public class JwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtApplication.class, args);
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("  JWT Authentication Service Started");
        System.out.println("  POST http://localhost:8081/auth/login");
        System.out.println("       Body: { username, password }");
        System.out.println("  GET  http://localhost:8081/api/secure/hello");
        System.out.println("       Header: Authorization: Bearer <token>");
        System.out.println("═══════════════════════════════════════════════════");
    }
}
