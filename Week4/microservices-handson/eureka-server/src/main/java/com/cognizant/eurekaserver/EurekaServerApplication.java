package com.cognizant.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Week 4 – Exercise 2
 * Task: Create Eureka Discovery Server and register microservices
 *
 * The Eureka Server acts as a Service Registry.
 * All microservices (account-service, loan-service) register themselves here.
 * The API Gateway uses Eureka to discover service instances dynamically.
 *
 * Dashboard: http://localhost:8761
 */
@SpringBootApplication
@EnableEurekaServer   // ← Activates the Eureka Server
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
        System.out.println("════════════════════════════════════════════════════");
        System.out.println("  ✅ Eureka Discovery Server Started");
        System.out.println("  Dashboard : http://localhost:8761");
        System.out.println("  Registered services will appear on the dashboard.");
        System.out.println("════════════════════════════════════════════════════");
    }
}
