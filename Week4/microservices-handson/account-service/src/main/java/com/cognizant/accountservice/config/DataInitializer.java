package com.cognizant.accountservice.config;

import com.cognizant.accountservice.model.Account;
import com.cognizant.accountservice.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Week 4 – Account Microservice
 *
 * Seeds demo data into the H2 database on startup.
 * Provides realistic sample accounts for testing.
 */
@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner seedAccountData(AccountRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new Account("ACC001001", 1L, "Vikas Srivastav",
                        Account.AccountType.SAVINGS,
                        new BigDecimal("125000.00"),
                        "Mumbai Main Branch", "SBIN0001234",
                        LocalDate.of(2020, 1, 15)));

                repo.save(new Account("ACC001002", 2L, "Priya Sharma",
                        Account.AccountType.CURRENT,
                        new BigDecimal("850000.50"),
                        "Delhi Connaught Place", "HDFC0004321",
                        LocalDate.of(2019, 6, 20)));

                repo.save(new Account("ACC001003", 3L, "Rahul Gupta",
                        Account.AccountType.SAVINGS,
                        new BigDecimal("45250.75"),
                        "Bangalore Koramangala", "ICIC0009876",
                        LocalDate.of(2021, 3, 10)));

                repo.save(new Account("ACC001004", 1L, "Vikas Srivastav",
                        Account.AccountType.FIXED_DEPOSIT,
                        new BigDecimal("500000.00"),
                        "Mumbai Main Branch", "SBIN0001234",
                        LocalDate.of(2022, 11, 1)));

                repo.save(new Account("ACC001005", 4L, "Anjali Singh",
                        Account.AccountType.RECURRING_DEPOSIT,
                        new BigDecimal("12000.00"),
                        "Pune FC Road", "AXIS0007654",
                        LocalDate.of(2023, 7, 5)));

                System.out.println("  ✅ Account Service: 5 demo accounts seeded into H2 DB.");
            }
        };
    }
}
