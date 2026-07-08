package com.cognizant.loanservice.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Week 4 – Loan Microservice
 *
 * JPA Entity representing a loan.
 */
@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "loan_number", unique = true, nullable = false, length = 20)
    private String loanNumber;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "loan_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private LoanType loanType;

    @Column(name = "principal_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal principalAmount;

    @Column(name = "outstanding_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal outstandingAmount;

    @Column(name = "emi_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal emiAmount;

    @Column(name = "interest_rate", nullable = false, precision = 5, scale = 2)
    private BigDecimal interestRate;

    @Column(name = "tenure_months", nullable = false)
    private int tenureMonths;

    @Column(name = "disbursement_date")
    private LocalDate disbursementDate;

    @Column(name = "maturity_date")
    private LocalDate maturityDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    @Column(name = "linked_account_number", length = 20)
    private String linkedAccountNumber;

    public enum LoanType {
        HOME_LOAN, PERSONAL_LOAN, CAR_LOAN,
        EDUCATION_LOAN, BUSINESS_LOAN, GOLD_LOAN
    }

    public enum LoanStatus {
        APPLIED, APPROVED, ACTIVE, CLOSED, REJECTED, DEFAULT
    }

    // ─── Constructors ───────────────────────────────────────────────────
    public Loan() {}

    public Loan(String loanNumber, Long customerId, String customerName,
                LoanType loanType, BigDecimal principalAmount,
                BigDecimal outstandingAmount, BigDecimal emiAmount,
                BigDecimal interestRate, int tenureMonths,
                LocalDate disbursementDate, LocalDate maturityDate,
                LoanStatus status, String linkedAccountNumber) {
        this.loanNumber          = loanNumber;
        this.customerId          = customerId;
        this.customerName        = customerName;
        this.loanType            = loanType;
        this.principalAmount     = principalAmount;
        this.outstandingAmount   = outstandingAmount;
        this.emiAmount           = emiAmount;
        this.interestRate        = interestRate;
        this.tenureMonths        = tenureMonths;
        this.disbursementDate    = disbursementDate;
        this.maturityDate        = maturityDate;
        this.status              = status;
        this.linkedAccountNumber = linkedAccountNumber;
    }

    // ─── Getters & Setters ──────────────────────────────────────────────
    public Long   getId()                        { return id; }
    public void   setId(Long id)                 { this.id = id; }

    public String getLoanNumber()                { return loanNumber; }
    public void   setLoanNumber(String n)        { this.loanNumber = n; }

    public Long   getCustomerId()                { return customerId; }
    public void   setCustomerId(Long c)          { this.customerId = c; }

    public String getCustomerName()              { return customerName; }
    public void   setCustomerName(String n)      { this.customerName = n; }

    public LoanType getLoanType()                { return loanType; }
    public void     setLoanType(LoanType t)      { this.loanType = t; }

    public BigDecimal getPrincipalAmount()             { return principalAmount; }
    public void       setPrincipalAmount(BigDecimal p) { this.principalAmount = p; }

    public BigDecimal getOutstandingAmount()             { return outstandingAmount; }
    public void       setOutstandingAmount(BigDecimal o) { this.outstandingAmount = o; }

    public BigDecimal getEmiAmount()             { return emiAmount; }
    public void       setEmiAmount(BigDecimal e) { this.emiAmount = e; }

    public BigDecimal getInterestRate()             { return interestRate; }
    public void       setInterestRate(BigDecimal r) { this.interestRate = r; }

    public int  getTenureMonths()              { return tenureMonths; }
    public void setTenureMonths(int t)         { this.tenureMonths = t; }

    public LocalDate getDisbursementDate()             { return disbursementDate; }
    public void      setDisbursementDate(LocalDate d)  { this.disbursementDate = d; }

    public LocalDate getMaturityDate()           { return maturityDate; }
    public void      setMaturityDate(LocalDate d){ this.maturityDate = d; }

    public LoanStatus getStatus()                { return status; }
    public void       setStatus(LoanStatus s)    { this.status = s; }

    public String getLinkedAccountNumber()           { return linkedAccountNumber; }
    public void   setLinkedAccountNumber(String a)   { this.linkedAccountNumber = a; }
}
