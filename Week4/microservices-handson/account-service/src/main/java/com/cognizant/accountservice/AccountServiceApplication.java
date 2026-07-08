package com.cognizant.accountservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Week 4 – Exercise 2
 * Task: Creating Microservices for account
 *
 * This microservice manages bank accounts.
 * It registers with the Eureka Discovery Server on startup.
 * Requests are routed to this service through the API Gateway.
 *
 * Base URL (direct): http://localhost:8081/api/accounts
 * Base URL (gateway): http://localhost:8080/account-service/api/accounts
 */
@SpringBootApplication
@EnableDiscoveryClient   // ← Registers this service with Eureka
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
        System.out.println("════════════════════════════════════════════════════");
        System.out.println("  ✅ Account Microservice Started");
        System.out.println("  Direct : http://localhost:8081/api/accounts");
        System.out.println("  Gateway: http://localhost:8080/account-service/api/accounts");
        System.out.println("  Registered with Eureka at: http://localhost:8761");
        System.out.println("════════════════════════════════════════════════════");
    }
}
