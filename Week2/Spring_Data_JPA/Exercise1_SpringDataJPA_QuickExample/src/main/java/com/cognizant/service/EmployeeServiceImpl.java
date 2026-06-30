package com.cognizant.service;

import com.cognizant.entity.Employee;
import com.cognizant.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Employee Service Implementation
 *
 * <p>Concrete implementation of {@link EmployeeService}, providing all
 * business-logic operations for managing {@link Employee} entities.</p>
 *
 * <p><b>Key Spring Annotations:</b>
 * <ul>
 *   <li>{@code @Service} – marks this class as a Spring-managed service bean;
 *       semantically equivalent to {@code @Component} but expresses intent.</li>
 *   <li>{@code @Transactional} – at the class level, wraps every public method
 *       in a transaction automatically. Individual methods can override the
 *       behaviour (e.g., {@code readOnly = true} for SELECT-only operations to
 *       skip the dirty-checking overhead).</li>
 *   <li>{@code @Autowired} – injects the {@link EmployeeRepository} bean via
 *       constructor injection (preferred over field injection for testability).</li>
 * </ul>
 * </p>
 *
 * <p><b>Transaction Management:</b>
 * Spring wraps each method call in a new transaction (or joins an existing one).
 * If a {@link RuntimeException} is thrown, Spring automatically rolls back the
 * transaction. Checked exceptions do NOT trigger rollback by default—use
 * {@code @Transactional(rollbackFor = Exception.class)} if needed.</p>
 *
 * @author  Cognizant Nurture – Java FSE Program
 * @version 1.0
 * @since   Week 2 – Spring Data JPA Module
 * @see     EmployeeService
 * @see     EmployeeRepository
 */
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    // =========================================================================
    // Dependencies
    // =========================================================================

    /**
     * Spring Data JPA repository providing all CRUD and custom query methods.
     * Injected via constructor injection (preferred Spring best practice).
     */
    private final EmployeeRepository employeeRepository;

    /**
     * Constructor injection of the {@link EmployeeRepository}.
     *
     * <p>Constructor injection is preferred over field injection because:
     * <ol>
     *   <li>Makes dependencies explicit and mandatory.</li>
     *   <li>Enables unit testing without a Spring context (pass a mock directly).</li>
     *   <li>Prevents circular dependency issues being hidden.</li>
     * </ol>
     * </p>
     *
     * @param employeeRepository the JPA repository bean, auto-wired by Spring
     */
    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // =========================================================================
    // CRUD Operations
    // =========================================================================

    /**
     * {@inheritDoc}
     *
     * <p>Delegates directly to {@link EmployeeRepository#save(Object)}.
     * Hibernate determines whether to issue an INSERT or UPDATE by checking
     * whether the entity's ID is {@code null} (new) or populated (existing).</p>
     */
    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Read-only transaction ({@code readOnly = true}) tells Hibernate to
     * skip the flush phase and dirty-checking, slightly improving performance
     * for SELECT-only operations.</p>
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Uses {@code readOnly = true} for performance—no modifications happen here.</p>
     */
    @Override
    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * {@inheritDoc}
     *
     * <p>Implementation steps:
     * <ol>
     *   <li>Fetch the existing entity from the DB (throws if not found).</li>
     *   <li>Update only the mutable business fields (not the PK).</li>
     *   <li>Save the modified entity—Hibernate issues an UPDATE SQL.</li>
     * </ol>
     * </p>
     *
     * @throws RuntimeException wrapping an informative message if the employee
     *         with the given {@code id} does not exist
     */
    @Override
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        // Step 1: Fetch the existing employee – throws if not found
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Employee not found with id: " + id));

        // Step 2: Apply the new field values
        existingEmployee.setFirstName(employeeDetails.getFirstName());
        existingEmployee.setLastName(employeeDetails.getLastName());
        existingEmployee.setEmail(employeeDetails.getEmail());
        existingEmployee.setDepartment(employeeDetails.getDepartment());
        existingEmployee.setSalary(employeeDetails.getSalary());

        // Step 3: Persist (UPDATE SQL is issued by Hibernate at flush time)
        return employeeRepository.save(existingEmployee);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Verifies existence before deletion to provide a meaningful error message
     * rather than silently ignoring a non-existent ID.</p>
     *
     * @throws RuntimeException if no employee with the given ID exists
     */
    @Override
    public void deleteEmployee(Long id) {
        // Verify the record exists before attempting deletion
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete – Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long countEmployees() {
        return employeeRepository.count();
    }

    // =========================================================================
    // Custom / Business Query Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Employee> getEmployeesByDepartment(String department) {
        return employeeRepository.findByDepartment(department);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Employee> searchEmployeesByName(String name) {
        return employeeRepository.findByFirstNameContainingIgnoreCase(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Employee> getEmployeesByEmailDomain(String domain) {
        return employeeRepository.findByEmailDomain(domain);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Employee> getEmployeesBySalaryGreaterThan(double minSalary) {
        return employeeRepository.findBySalaryGreaterThanEqual(minSalary);
    }
}
