package com.cognizant.repository;

import com.cognizant.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Employee Repository
 *
 * <p>Spring Data JPA repository for the {@link Employee} entity.
 * By extending {@link JpaRepository}, this interface automatically receives
 * implementations for all standard CRUD operations at startup—without writing
 * a single line of boilerplate DAO code:</p>
 *
 * <ul>
 *   <li>{@code save(entity)}          - Insert or Update</li>
 *   <li>{@code findById(id)}          - Read by PK</li>
 *   <li>{@code findAll()}             - Read all rows</li>
 *   <li>{@code deleteById(id)}        - Delete by PK</li>
 *   <li>{@code count()}               - Count rows</li>
 *   <li>{@code existsById(id)}        - Existence check</li>
 * </ul>
 *
 * <p>In addition to inherited methods, this interface defines:</p>
 * <ol>
 *   <li><b>Derived Query Methods</b> – Spring Data JPA parses the method name
 *       at startup and generates the JPQL/SQL automatically. No {@code @Query}
 *       annotation is needed.</li>
 *   <li><b>@Query Methods</b> – Explicit JPQL written by the developer, used
 *       when derived queries would be too verbose or impossible to express.</li>
 * </ol>
 *
 * <p><b>JpaRepository generic parameters:</b>
 * <ul>
 *   <li>{@code Employee} – the managed entity type</li>
 *   <li>{@code Long}     – the type of the entity's primary key ({@code @Id})</li>
 * </ul>
 * </p>
 *
 * @author  Cognizant Nurture – Java FSE Program
 * @version 1.0
 * @since   Week 2 – Spring Data JPA Module
 * @see     Employee
 * @see     JpaRepository
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // =========================================================================
    // Derived Query Methods (Method-Name Based)
    // Spring Data JPA translates these method signatures into SQL at startup.
    // =========================================================================

    /**
     * Finds all employees belonging to the specified department.
     *
     * <p>Spring Data JPA generates the equivalent JPQL:
     * {@code SELECT e FROM Employee e WHERE e.department = :department}</p>
     *
     * <p>The parameter name must match the entity field name exactly (case-sensitive).
     * The column mapping is handled transparently by Hibernate.</p>
     *
     * @param department the department name to filter by (e.g., "Engineering")
     * @return a list of {@code Employee} objects in that department;
     *         returns an empty list (never {@code null}) if none found
     */
    List<Employee> findByDepartment(String department);

    /**
     * Finds employees whose first name contains the given substring,
     * performing a case-insensitive match.
     *
     * <p>Generated JPQL equivalent:
     * {@code SELECT e FROM Employee e WHERE LOWER(e.firstName) LIKE LOWER(CONCAT('%',:name,'%'))}</p>
     *
     * <p>Breakdown of the method name tokens:
     * <ul>
     *   <li>{@code findBy}           - indicates a SELECT + WHERE query</li>
     *   <li>{@code FirstName}        - maps to the {@code firstName} field</li>
     *   <li>{@code Containing}       - adds a {@code LIKE '%value%'} predicate</li>
     *   <li>{@code IgnoreCase}       - wraps in {@code LOWER()} for case-insensitivity</li>
     * </ul>
     * </p>
     *
     * @param name the substring to search for within first names
     * @return list of matching {@code Employee} records; empty list if none found
     */
    List<Employee> findByFirstNameContainingIgnoreCase(String name);

    /**
     * Finds all employees whose email address belongs to a given domain.
     *
     * <p>This uses an explicit JPQL {@code @Query} because the derived query
     * syntax cannot express a {@code LIKE} pattern built from a parameter.
     * JPQL operates on entity field names ({@code e.email}), not column names.</p>
     *
     * <p>Example: calling {@code findByEmailDomain("techcorp.com")} would match
     * {@code alice.johnson@techcorp.com}, {@code bob.smith@techcorp.com}, etc.</p>
     *
     * <p>The {@code CONCAT('%@', :domain)} builds the pattern {@code %@techcorp.com}
     * ensuring we match only the domain portion after the "@" symbol.</p>
     *
     * @param domain the email domain to search for (e.g., {@code "techcorp.com"})
     * @return list of {@code Employee} records whose email ends with {@code @domain}
     */
    @Query("SELECT e FROM Employee e WHERE e.email LIKE CONCAT('%@', :domain)")
    List<Employee> findByEmailDomain(@Param("domain") String domain);

    /**
     * Finds all employees whose salary is greater than or equal to the given value.
     *
     * <p>Generated JPQL: {@code SELECT e FROM Employee e WHERE e.salary >= :minSalary}
     * (Spring translates {@code GreaterThanEqual} keyword automatically)</p>
     *
     * @param minSalary the minimum salary threshold
     * @return list of employees earning at or above {@code minSalary}
     */
    List<Employee> findBySalaryGreaterThanEqual(double minSalary);

    /**
     * Finds employees by department name, case-insensitively, ordered by last name.
     *
     * <p>Uses explicit JPQL for the ordering clause, which is simpler than the
     * very long derived-query equivalent.</p>
     *
     * @param department the department name (case-insensitive)
     * @return alphabetically ordered list of employees in the given department
     */
    @Query("SELECT e FROM Employee e WHERE LOWER(e.department) = LOWER(:department) ORDER BY e.lastName ASC")
    List<Employee> findByDepartmentIgnoreCaseOrderByLastName(@Param("department") String department);
}
