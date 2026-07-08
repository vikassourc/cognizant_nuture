package com.cognizant.accountservice.controller;

import com.cognizant.accountservice.model.Account;
import com.cognizant.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Week 4 – Exercise 2
 * Task: Creating Microservices for account
 *
 * REST Controller for Account operations.
 * Accessed directly at port 8081, or via API Gateway at port 8080.
 *
 * Endpoints:
 *   GET    /api/accounts           → List all accounts
 *   GET    /api/accounts/{id}      → Get account by ID
 *   GET    /api/accounts/number/{num} → Get by account number
 *   GET    /api/accounts/customer/{id} → Get by customer ID
 *   POST   /api/accounts           → Create new account
 *   PUT    /api/accounts/{id}      → Update account
 *   DELETE /api/accounts/{id}      → Delete account
 *   GET    /api/accounts/info      → Service info (for Eureka demo)
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    /** Inject service instance info for demo purposes */
    @Value("${server.port}")
    private String serverPort;

    @Value("${spring.application.name}")
    private String serviceName;

    // ─────────────────────────────────────────────────────────────────────
    //  Service Info – demonstrates which instance is serving the request
    // ─────────────────────────────────────────────────────────────────────

    /**
     * GET /api/accounts/info
     * Returns metadata about this microservice instance.
     * Useful for demonstrating Eureka load balancing.
     */
    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> serviceInfo() {
        return ResponseEntity.ok(Map.of(
                "service",     serviceName,
                "port",        serverPort,
                "status",      "UP",
                "description", "Account Microservice – handles bank account operations",
                "endpoints",   "GET /api/accounts, POST /api/accounts, GET /api/accounts/{id}"
        ));
    }

    // ─────────────────────────────────────────────────────────────────────
    //  CRUD Endpoints
    // ─────────────────────────────────────────────────────────────────────

    /** GET /api/accounts – Returns all accounts */
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    /** GET /api/accounts/{id} – Returns account by ID */
    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Account not found with id: " + id)));
    }

    /** GET /api/accounts/number/{accountNumber} – Returns account by number */
    @GetMapping("/number/{accountNumber}")
    public ResponseEntity<?> getAccountByNumber(@PathVariable String accountNumber) {
        return accountService.getAccountByNumber(accountNumber)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Account not found: " + accountNumber)));
    }

    /** GET /api/accounts/customer/{customerId} – Returns all accounts for a customer */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Account>> getByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(accountService.getAccountsByCustomerId(customerId));
    }

    /** POST /api/accounts – Creates a new account */
    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        try {
            Account created = accountService.createAccount(account);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /** PUT /api/accounts/{id} – Updates an existing account */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable Long id,
                                            @RequestBody Account updated) {
        return accountService.updateAccount(id, updated)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Account not found with id: " + id)));
    }

    /** DELETE /api/accounts/{id} – Deletes an account */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteAccount(@PathVariable Long id) {
        boolean deleted = accountService.deleteAccount(id);
        if (deleted) {
            return ResponseEntity.ok(Map.of("message", "Account " + id + " deleted successfully."));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Account not found with id: " + id));
    }
}
