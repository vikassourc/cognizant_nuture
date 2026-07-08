package com.cognizant.loanservice.repository;

import com.cognizant.loanservice.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Week 4 – Loan Microservice
 * Spring Data JPA Repository for Loan entity.
 */
@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    Optional<Loan> findByLoanNumber(String loanNumber);
    List<Loan>     findByCustomerId(Long customerId);
    List<Loan>     findByStatus(Loan.LoanStatus status);
    List<Loan>     findByLoanType(Loan.LoanType loanType);
    boolean        existsByLoanNumber(String loanNumber);
}
