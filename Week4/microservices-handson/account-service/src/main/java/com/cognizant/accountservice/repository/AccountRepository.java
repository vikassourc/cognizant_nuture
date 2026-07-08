package com.cognizant.accountservice.repository;

import com.cognizant.accountservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Week 4 – Account Microservice
 *
 * Spring Data JPA Repository for Account entity.
 * Provides CRUD operations out of the box.
 * Custom query methods follow Spring Data naming conventions.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /** Find account by account number */
    Optional<Account> findByAccountNumber(String accountNumber);

    /** Find all accounts for a specific customer */
    List<Account> findByCustomerId(Long customerId);

    /** Find all active/inactive accounts */
    List<Account> findByActive(boolean active);

    /** Find accounts by type (SAVINGS, CURRENT, etc.) */
    List<Account> findByAccountType(Account.AccountType accountType);

    /** Check if account number already exists */
    boolean existsByAccountNumber(String accountNumber);
}
