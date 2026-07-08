package com.cognizant.springrest.config;

import com.cognizant.springrest.model.Country;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * Week 3 – Exercise 1 (Day 26)
 * Task: Spring Core – Load Country from Spring Configuration XML
 *
 * Java @Configuration class that bridges the classic XML context
 * into the Spring Boot context.
 *
 * It loads spring-config.xml using ClassPathXmlApplicationContext
 * and exposes the country list as a Spring-managed bean.
 */
@Configuration
public class SpringXmlLoader {

    /**
     * Loads all Country beans defined in spring-config.xml
     * and exposes them as a List<Country> bean named "countryList".
     *
     * Demonstrates: Spring Core IoC, XML-based bean wiring,
     *               ApplicationContext usage.
     */
    @Bean(name = "countryList")
    public List<Country> countryList() {
        // Load the classic XML-based Spring ApplicationContext
        ApplicationContext xmlContext =
                new ClassPathXmlApplicationContext("spring-config.xml");

        System.out.println("─────────────────────────────────────────────────────");
        System.out.println("  Loading Countries from Spring XML Configuration...");
        System.out.println("─────────────────────────────────────────────────────");

        // Retrieve each country bean by its bean ID
        Country india     = xmlContext.getBean("india",     Country.class);
        Country usa       = xmlContext.getBean("usa",       Country.class);
        Country uk        = xmlContext.getBean("uk",        Country.class);
        Country australia = xmlContext.getBean("australia", Country.class);
        Country canada    = xmlContext.getBean("canada",    Country.class);

        List<Country> countries = Arrays.asList(india, usa, uk, australia, canada);
        countries.forEach(c -> System.out.println("  Loaded: " + c));
        System.out.println("─────────────────────────────────────────────────────");

        return countries;
    }
}
