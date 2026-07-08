package com.cognizant.loanservice.config;

import com.cognizant.loanservice.model.Loan;
import com.cognizant.loanservice.repository.LoanRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Week 4 – Loan Microservice
 * Seeds demo loan data into H2 database on startup.
 */
@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner seedLoanData(LoanRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new Loan("LN2024001", 1L, "Vikas Srivastav",
                        Loan.LoanType.HOME_LOAN,
                        new BigDecimal("5000000.00"),
                        new BigDecimal("4250000.00"),
                        new BigDecimal("42500.00"),
                        new BigDecimal("8.5"),
                        180,
                        LocalDate.of(2024, 1, 1),
                        LocalDate.of(2039, 1, 1),
                        Loan.LoanStatus.ACTIVE,
                        "ACC001001"));

                repo.save(new Loan("LN2024002", 2L, "Priya Sharma",
                        Loan.LoanType.CAR_LOAN,
                        new BigDecimal("800000.00"),
                        new BigDecimal("600000.00"),
                        new BigDecimal("16500.00"),
                        new BigDecimal("9.0"),
                        60,
                        LocalDate.of(2024, 3, 15),
                        LocalDate.of(2029, 3, 15),
                        Loan.LoanStatus.ACTIVE,
                        "ACC001002"));

                repo.save(new Loan("LN2023001", 3L, "Rahul Gupta",
                        Loan.LoanType.PERSONAL_LOAN,
                        new BigDecimal("200000.00"),
                        new BigDecimal("80000.00"),
                        new BigDecimal("7500.00"),
                        new BigDecimal("12.0"),
                        36,
                        LocalDate.of(2023, 6, 1),
                        LocalDate.of(2026, 6, 1),
                        Loan.LoanStatus.ACTIVE,
                        "ACC001003"));

                repo.save(new Loan("LN2023002", 4L, "Anjali Singh",
                        Loan.LoanType.EDUCATION_LOAN,
                        new BigDecimal("1500000.00"),
                        new BigDecimal("1500000.00"),
                        new BigDecimal("0.00"),
                        new BigDecimal("7.0"),
                        120,
                        LocalDate.of(2023, 9, 1),
                        LocalDate.of(2033, 9, 1),
                        Loan.LoanStatus.APPROVED,
                        "ACC001005"));

                repo.save(new Loan("LN2022001", 2L, "Priya Sharma",
                        Loan.LoanType.BUSINESS_LOAN,
                        new BigDecimal("3000000.00"),
                        new BigDecimal("0.00"),
                        new BigDecimal("0.00"),
                        new BigDecimal("11.5"),
                        48,
                        LocalDate.of(2020, 5, 1),
                        LocalDate.of(2024, 5, 1),
                        Loan.LoanStatus.CLOSED,
                        "ACC001002"));

                System.out.println("  ✅ Loan Service: 5 demo loans seeded into H2 DB.");
            }
        };
    }
}
