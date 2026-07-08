package com.cognizant.loanservice.service;

import com.cognizant.loanservice.model.Loan;
import com.cognizant.loanservice.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Week 4 – Loan Microservice
 * Service layer for Loan business logic.
 */
@Service
public class LoanService {

    private final LoanRepository loanRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<Loan>     getAllLoans()                          { return loanRepository.findAll(); }
    public Optional<Loan> getLoanById(Long id)                  { return loanRepository.findById(id); }
    public Optional<Loan> getLoanByNumber(String loanNumber)    { return loanRepository.findByLoanNumber(loanNumber); }
    public List<Loan>     getLoansByCustomerId(Long customerId) { return loanRepository.findByCustomerId(customerId); }
    public List<Loan>     getLoansByStatus(Loan.LoanStatus s)  { return loanRepository.findByStatus(s); }

    public Loan createLoan(Loan loan) {
        if (loanRepository.existsByLoanNumber(loan.getLoanNumber())) {
            throw new IllegalArgumentException(
                "Loan number '" + loan.getLoanNumber() + "' already exists.");
        }
        return loanRepository.save(loan);
    }

    public Optional<Loan> updateLoan(Long id, Loan updated) {
        return loanRepository.findById(id).map(existing -> {
            existing.setLoanType(updated.getLoanType());
            existing.setPrincipalAmount(updated.getPrincipalAmount());
            existing.setOutstandingAmount(updated.getOutstandingAmount());
            existing.setEmiAmount(updated.getEmiAmount());
            existing.setInterestRate(updated.getInterestRate());
            existing.setTenureMonths(updated.getTenureMonths());
            existing.setStatus(updated.getStatus());
            existing.setMaturityDate(updated.getMaturityDate());
            return loanRepository.save(existing);
        });
    }

    public Optional<Loan> updateLoanStatus(Long id, Loan.LoanStatus newStatus) {
        return loanRepository.findById(id).map(loan -> {
            loan.setStatus(newStatus);
            return loanRepository.save(loan);
        });
    }

    public boolean deleteLoan(Long id) {
        if (loanRepository.existsById(id)) {
            loanRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
