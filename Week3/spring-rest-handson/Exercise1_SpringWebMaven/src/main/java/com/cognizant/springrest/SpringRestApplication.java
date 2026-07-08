package com.cognizant.springrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Week 3 – Exercise 1 (Day 25)
 * Task: Create a Spring Web Project using Maven
 *
 * Entry point for the Spring Boot application.
 * @SpringBootApplication enables:
 *   - @Configuration   (bean definitions)
 *   - @EnableAutoConfiguration (Spring Boot auto-config)
 *   - @ComponentScan   (component scanning)
 */
@SpringBootApplication
public class SpringRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRestApplication.class, args);
        System.out.println("✅ Spring REST Application started successfully!");
        System.out.println("   Visit: http://localhost:8080/api/hello");
        System.out.println("   Visit: http://localhost:8080/api/country/all");
    }
}
