package com.cognizant.LibraryManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Java-based configuration class for the Library Management System.
 *
 * <p>This class replaces the traditional {@code applicationContext.xml} file
 * with a type-safe, refactor-friendly Java configuration. It uses Spring
 * annotations to define the application context:</p>
 *
 * <ul>
 *   <li>{@link Configuration} - Marks this as a Spring configuration class
 *       (analogous to an XML {@code <beans>} document).</li>
 *   <li>{@link ComponentScan} - Instructs Spring to scan the given package
 *       and automatically register classes annotated with {@code @Component},
 *       {@code @Service}, {@code @Repository}, {@code @Controller}, etc.</li>
 *   <li>{@link Bean} - Marks a method whose return value is registered as
 *       a Spring bean (analogous to XML {@code <bean>} elements).</li>
 * </ul>
 *
 * @author Cognizant Nurture - Java FSE
 * @version 1.0
 * @since Week 2 - Spring Core and Maven
 */
@Configuration
@ComponentScan(basePackages = "com.cognizant")
public class AppConfig {

    /**
     * Demonstrates a manually declared {@code @Bean} method.
     *
     * <p>Although {@code BookRepositoryImpl} and {@code BookServiceImpl} are
     * auto-discovered via {@code @ComponentScan}, you can also define beans
     * explicitly here. This is useful for third-party classes you cannot annotate
     * or for beans requiring complex initialization logic.</p>
     *
     * <p>In this exercise, component scanning handles auto-registration,
     * so this method is provided as an illustrative example only.
     * Spring will use the {@code @Repository}-annotated implementation.</p>
     *
     * @return a descriptive string bean named "applicationDescription"
     */
    @Bean
    public String applicationDescription() {
        return "Library Management System - Annotation-based Spring Configuration (Exercise 4)";
    }
}
