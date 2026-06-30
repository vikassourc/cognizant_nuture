package com.cognizant.service;

import com.cognizant.entity.Employee;

import java.util.List;
import java.util.Optional;

/**
 * Employee Service Interface
 *
 * <p>Defines the contract for all business-logic operations related to
 * {@link Employee} entities. The service layer sits between the presentation
 * layer (controllers / CLI runners) and the data-access layer (repositories),
 * enforcing the <em>Single Responsibility Principle</em>:</p>
 *
 * <ul>
 *   <li>The <b>repository</b> layer handles raw DB interaction.</li>
 *   <li>The <b>service</b> layer handles business rules, validation,
 *       and transaction boundaries.</li>
 *   <li>The <b>controller / runner</b> layer handles user-facing I/O.</li>
 * </ul>
 *
 * <p>By programming to this interface rather than a concrete class, the rest
 * of the application stays loosely coupled—swapping out the implementation
 * (e.g., from JPA to a NoSQL store) requires zero changes in the callers.</p>
 *
 * @author  Cognizant Nurture – Java FSE Program
 * @version 1.0
 * @since   Week 2 – Spring Data JPA Module
 * @see     com.cognizant.service.EmployeeServiceImpl
 */
public interface EmployeeService {

    // =========================================================================
    // CRUD Operations
    // =========================================================================

    /**
     * Saves (inserts or updates) an employee record.
     *
     * <p>If the {@code Employee} has a {@code null} ID, a new record is inserted.
     * If it has an existing ID, the matching record is updated (merge semantics).</p>
     *
     * @param employee the employee object to persist; must not be {@code null}
     * @return the saved {@code Employee} with its auto-generated ID populated
     */
    Employee saveEmployee(Employee employee);

    /**
     * Retrieves an employee by its primary key.
     *
     * @param id the database ID of the employee
     * @return an {@link Optional} containing the employee if found,
     *         or {@link Optional#empty()} if no record exists for that ID
     */
    Optional<Employee> getEmployeeById(Long id);

    /**
     * Retrieves all employee records from the database.
     *
     * @return a {@link List} of all {@code Employee} objects;
     *         returns an empty list if the table is empty
     */
    List<Employee> getAllEmployees();

    /**
     * Updates the mutable fields of an existing employee record.
     *
     * <p>Looks up the record by {@code id}, applies the updated values from the
     * supplied {@code Employee} object, and saves the merged result.</p>
     *
     * @param id             the ID of the employee to update
     * @param employeeDetails an {@code Employee} object carrying the new field values
     * @return the updated and re-persisted {@code Employee}
     * @throws RuntimeException if no employee exists with the given {@code id}
     */
    Employee updateEmployee(Long id, Employee employeeDetails);

    /**
     * Deletes an employee record by its primary key.
     *
     * @param id the ID of the employee to delete
     * @throws RuntimeException if no employee exists with the given {@code id}
     */
    void deleteEmployee(Long id);

    /**
     * Returns the total count of employee records in the database.
     *
     * @return number of rows in the employees table
     */
    long countEmployees();

    // =========================================================================
    // Custom / Business Query Methods
    // =========================================================================

    /**
     * Finds all employees belonging to a specific department.
     *
     * @param department the department name (exact match, case-sensitive)
     * @return list of matching employees; empty list if none found
     */
    List<Employee> getEmployeesByDepartment(String department);

    /**
     * Finds employees whose first name contains the given search term,
     * regardless of letter casing.
     *
     * @param name a substring to search for in the first name field
     * @return list of matching employees; empty list if none found
     */
    List<Employee> searchEmployeesByName(String name);

    /**
     * Finds employees whose email address belongs to the specified domain.
     *
     * @param domain the email domain to filter by (e.g., {@code "techcorp.com"})
     * @return list of employees with a matching email domain
     */
    List<Employee> getEmployeesByEmailDomain(String domain);

    /**
     * Finds all employees earning at or above the given salary threshold.
     *
     * @param minSalary the minimum salary (inclusive)
     * @return list of qualifying employees; empty list if none found
     */
    List<Employee> getEmployeesBySalaryGreaterThan(double minSalary);
}
