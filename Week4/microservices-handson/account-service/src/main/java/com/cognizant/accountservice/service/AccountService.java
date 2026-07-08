package com.cognizant.accountservice.service;

import com.cognizant.accountservice.model.Account;
import com.cognizant.accountservice.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Week 4 – Account Microservice
 *
 * Service layer for Account business logic.
 * Separates business rules from the REST controller.
 */
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /** Get all accounts */
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    /** Get account by internal ID */
    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    /** Get account by account number */
    public Optional<Account> getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    /** Get all accounts belonging to a customer */
    public List<Account> getAccountsByCustomerId(Long customerId) {
        return accountRepository.findByCustomerId(customerId);
    }

    /** Create a new account */
    public Account createAccount(Account account) {
        if (accountRepository.existsByAccountNumber(account.getAccountNumber())) {
            throw new IllegalArgumentException(
                "Account number '" + account.getAccountNumber() + "' already exists.");
        }
        return accountRepository.save(account);
    }

    /** Update an existing account */
    public Optional<Account> updateAccount(Long id, Account updated) {
        return accountRepository.findById(id).map(existing -> {
            existing.setCustomerName(updated.getCustomerName());
            existing.setAccountType(updated.getAccountType());
            existing.setBalance(updated.getBalance());
            existing.setBranchName(updated.getBranchName());
            existing.setIfscCode(updated.getIfscCode());
            existing.setActive(updated.isActive());
            return accountRepository.save(existing);
        });
    }

    /** Deactivate (soft-delete) an account */
    public boolean deactivateAccount(Long id) {
        return accountRepository.findById(id).map(account -> {
            account.setActive(false);
            accountRepository.save(account);
            return true;
        }).orElse(false);
    }

    /** Hard-delete an account */
    public boolean deleteAccount(Long id) {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /** Get all active accounts */
    public List<Account> getActiveAccounts() {
        return accountRepository.findByActive(true);
    }
}
