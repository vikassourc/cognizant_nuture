package com.cognizant.accountservice.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Week 4 – Account Microservice
 *
 * JPA Entity representing a bank account.
 * Persisted in H2 in-memory database for demo purposes.
 */
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", unique = true, nullable = false, length = 20)
    private String accountNumber;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "account_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "balance", nullable = false, precision = 15, scale = 2)
    private BigDecimal balance;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "ifsc_code", length = 11)
    private String ifscCode;

    @Column(name = "opened_date")
    private LocalDate openedDate;

    @Column(name = "active")
    private boolean active = true;

    /** Account types supported */
    public enum AccountType {
        SAVINGS, CURRENT, FIXED_DEPOSIT, RECURRING_DEPOSIT
    }

    // ─── Constructors ───────────────────────────────────────────────────
    public Account() {}

    public Account(String accountNumber, Long customerId, String customerName,
                   AccountType accountType, BigDecimal balance,
                   String branchName, String ifscCode, LocalDate openedDate) {
        this.accountNumber = accountNumber;
        this.customerId    = customerId;
        this.customerName  = customerName;
        this.accountType   = accountType;
        this.balance       = balance;
        this.branchName    = branchName;
        this.ifscCode      = ifscCode;
        this.openedDate    = openedDate;
        this.active        = true;
    }

    // ─── Getters & Setters ──────────────────────────────────────────────
    public Long getId()                          { return id; }
    public void setId(Long id)                   { this.id = id; }

    public String getAccountNumber()                     { return accountNumber; }
    public void   setAccountNumber(String n)             { this.accountNumber = n; }

    public Long   getCustomerId()                        { return customerId; }
    public void   setCustomerId(Long c)                  { this.customerId = c; }

    public String getCustomerName()                      { return customerName; }
    public void   setCustomerName(String n)              { this.customerName = n; }

    public AccountType getAccountType()                  { return accountType; }
    public void        setAccountType(AccountType t)     { this.accountType = t; }

    public BigDecimal getBalance()                       { return balance; }
    public void       setBalance(BigDecimal b)           { this.balance = b; }

    public String getBranchName()                        { return branchName; }
    public void   setBranchName(String b)                { this.branchName = b; }

    public String getIfscCode()                          { return ifscCode; }
    public void   setIfscCode(String i)                  { this.ifscCode = i; }

    public LocalDate getOpenedDate()                     { return openedDate; }
    public void      setOpenedDate(LocalDate d)          { this.openedDate = d; }

    public boolean isActive()                            { return active; }
    public void    setActive(boolean a)                  { this.active = a; }

    @Override
    public String toString() {
        return "Account{id=" + id + ", accountNumber='" + accountNumber
                + "', customerName='" + customerName + "', type=" + accountType
                + ", balance=" + balance + "}";
    }
}
