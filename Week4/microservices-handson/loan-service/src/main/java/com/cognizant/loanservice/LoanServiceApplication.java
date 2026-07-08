package com.cognizant.loanservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Week 4 – Exercise 2
 * Task: Creating Microservices for loan
 *
 * This microservice manages loan applications and repayments.
 * Registers with Eureka Discovery Server on startup.
 *
 * Base URL (direct): http://localhost:8082/api/loans
 * Base URL (gateway): http://localhost:8080/loan-service/api/loans
 */
@SpringBootApplication
@EnableDiscoveryClient
public class LoanServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoanServiceApplication.class, args);
        System.out.println("════════════════════════════════════════════════════");
        System.out.println("  ✅ Loan Microservice Started");
        System.out.println("  Direct : http://localhost:8082/api/loans");
        System.out.println("  Gateway: http://localhost:8080/loan-service/api/loans");
        System.out.println("  Registered with Eureka at: http://localhost:8761");
        System.out.println("════════════════════════════════════════════════════");
    }
}
