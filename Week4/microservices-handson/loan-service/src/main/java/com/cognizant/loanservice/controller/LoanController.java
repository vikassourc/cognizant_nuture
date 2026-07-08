package com.cognizant.loanservice.controller;

import com.cognizant.loanservice.model.Loan;
import com.cognizant.loanservice.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Week 4 – Exercise 2
 * Task: Creating Microservices for loan
 *
 * REST Controller for Loan operations.
 *
 * Endpoints:
 *   GET    /api/loans               → All loans
 *   GET    /api/loans/{id}          → Loan by ID
 *   GET    /api/loans/number/{num}  → Loan by number
 *   GET    /api/loans/customer/{id} → Loans by customer
 *   GET    /api/loans/status/{s}    → Loans by status
 *   POST   /api/loans               → Apply for a loan
 *   PUT    /api/loans/{id}          → Update loan
 *   PATCH  /api/loans/{id}/status   → Update loan status
 *   DELETE /api/loans/{id}          → Delete loan
 *   GET    /api/loans/info          → Service info
 */
@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Value("${server.port}")
    private String serverPort;

    @Value("${spring.application.name}")
    private String serviceName;

    /** GET /api/loans/info – Microservice metadata */
    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> serviceInfo() {
        return ResponseEntity.ok(Map.of(
                "service",     serviceName,
                "port",        serverPort,
                "status",      "UP",
                "description", "Loan Microservice – handles loan applications & repayments",
                "endpoints",   "GET /api/loans, POST /api/loans, PATCH /api/loans/{id}/status"
        ));
    }

    /** GET /api/loans – All loans */
    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    /** GET /api/loans/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<?> getLoanById(@PathVariable Long id) {
        return loanService.getLoanById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Loan not found with id: " + id)));
    }

    /** GET /api/loans/number/{loanNumber} */
    @GetMapping("/number/{loanNumber}")
    public ResponseEntity<?> getLoanByNumber(@PathVariable String loanNumber) {
        return loanService.getLoanByNumber(loanNumber)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Loan not found: " + loanNumber)));
    }

    /** GET /api/loans/customer/{customerId} */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Loan>> getLoansByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(loanService.getLoansByCustomerId(customerId));
    }

    /** GET /api/loans/status/{status} */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Loan>> getLoansByStatus(@PathVariable String status) {
        try {
            Loan.LoanStatus loanStatus = Loan.LoanStatus.valueOf(status.toUpperCase());
            return ResponseEntity.ok(loanService.getLoansByStatus(loanStatus));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /** POST /api/loans – Apply for new loan */
    @PostMapping
    public ResponseEntity<?> createLoan(@RequestBody Loan loan) {
        try {
            Loan created = loanService.createLoan(loan);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /** PUT /api/loans/{id} – Update loan */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLoan(@PathVariable Long id, @RequestBody Loan updated) {
        return loanService.updateLoan(id, updated)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Loan not found with id: " + id)));
    }

    /** PATCH /api/loans/{id}/status?status=APPROVED – Update loan status only */
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateLoanStatus(@PathVariable Long id,
                                               @RequestParam String status) {
        try {
            Loan.LoanStatus newStatus = Loan.LoanStatus.valueOf(status.toUpperCase());
            return loanService.updateLoanStatus(id, newStatus)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(Map.of("error", "Loan not found with id: " + id)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Invalid status: " + status
                                 + ". Valid: APPLIED, APPROVED, ACTIVE, CLOSED, REJECTED, DEFAULT"));
        }
    }

    /** DELETE /api/loans/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteLoan(@PathVariable Long id) {
        boolean deleted = loanService.deleteLoan(id);
        if (deleted) {
            return ResponseEntity.ok(Map.of("message", "Loan " + id + " deleted successfully."));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Loan not found with id: " + id));
    }
}
