package com.cognizant.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Week 4 – Exercise 2
 * Task: Microservices with API Gateway
 *
 * Spring Cloud Gateway acts as the single entry point for all clients.
 * It uses Eureka for service discovery and load balancing.
 *
 * Routing:
 *   /account-service/**  →  lb://account-service  (Eureka load-balanced)
 *   /loan-service/**     →  lb://loan-service
 *
 * Client calls:
 *   http://localhost:8080/account-service/api/accounts
 *   http://localhost:8080/loan-service/api/loans
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
        System.out.println("════════════════════════════════════════════════════");
        System.out.println("  ✅ API Gateway Started on port 8080");
        System.out.println("  Account Service : http://localhost:8080/account-service/api/accounts");
        System.out.println("  Loan Service    : http://localhost:8080/loan-service/api/loans");
        System.out.println("  Eureka Dashboard: http://localhost:8761");
        System.out.println("════════════════════════════════════════════════════");
    }
}
