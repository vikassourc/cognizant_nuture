package com.cognizant;

import com.cognizant.entity.Employee;
import com.cognizant.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Quick Example – Main Application
 *
 * <p>Entry point for the Cognizant Nurture Week 2 Spring Data JPA exercise.
 * This class serves two purposes:</p>
 * <ol>
 *   <li><b>Bootstrap the Spring context</b> – {@code @SpringBootApplication}
 *       enables component scanning, auto-configuration, and configuration
 *       properties support in one annotation (equivalent to
 *       {@code @Configuration + @EnableAutoConfiguration + @ComponentScan}).</li>
 *   <li><b>Run a live CRUD demo</b> – by implementing {@link CommandLineRunner},
 *       the {@code run()} method is invoked automatically after the application
 *       context is fully initialized, demonstrating all repository operations.</li>
 * </ol>
 *
 * <p><b>Demo sequence performed on startup:</b>
 * <ol>
 *   <li>Create and save new employees (INSERT)</li>
 *   <li>Retrieve all employees (SELECT *)</li>
 *   <li>Find by department (WHERE clause)</li>
 *   <li>Search by name (LIKE, case-insensitive)</li>
 *   <li>Find by email domain (custom @Query)</li>
 *   <li>Find by salary range</li>
 *   <li>Update one employee (UPDATE)</li>
 *   <li>Delete one employee (DELETE)</li>
 *   <li>Confirm final state</li>
 * </ol>
 * </p>
 *
 * <p><b>H2 Console:</b> While the application is running, you can inspect the
 * in-memory database at {@code http://localhost:8080/h2-console} using the
 * JDBC URL {@code jdbc:h2:mem:employeedb}.</p>
 *
 * @author  Cognizant Nurture – Java FSE Program
 * @version 1.0
 * @since   Week 2 – Spring Data JPA Module
 */
@SpringBootApplication
public class SpringDataJpaQuickExampleApplication implements CommandLineRunner {

    // =========================================================================
    // Dependencies
    // =========================================================================

    /** Service layer bean that handles all Employee business operations. */
    @Autowired
    private EmployeeService employeeService;

    // =========================================================================
    // Main Method
    // =========================================================================

    /**
     * Application entry point.
     *
     * <p>Delegates to {@link SpringApplication#run(Class, String...)} which:
     * <ol>
     *   <li>Creates an {@code ApplicationContext}</li>
     *   <li>Registers all beans found via component scanning</li>
     *   <li>Starts the embedded Tomcat server</li>
     *   <li>Calls {@code CommandLineRunner.run()} on all registered runners</li>
     * </ol>
     * </p>
     *
     * @param args command-line arguments passed to the JVM (not used here)
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaQuickExampleApplication.class, args);
    }

    // =========================================================================
    // CommandLineRunner - Demo All CRUD Operations
    // =========================================================================

    /**
     * Executed automatically by Spring Boot after the application context starts.
     *
     * <p>Demonstrates the full lifecycle of CRUD operations using Spring Data JPA
     * and prints formatted results to the console.</p>
     *
     * @param args command-line arguments (not used)
     * @throws Exception if any database operation fails
     */
    @Override
    public void run(String... args) throws Exception {

        printBanner("SPRING DATA JPA - QUICK EXAMPLE DEMO");
        System.out.println("Note: 5 employees were also inserted via data.sql at startup.\n");

        // =================================================================
        // STEP 1: CREATE – Save new Employee records
        // =================================================================
        printSection("STEP 1: CREATE – Saving new Employees via service layer");

        Employee frank = new Employee("Frank", "Miller", "frank.miller@newco.org",
                "Engineering", 105000.00);
        Employee grace = new Employee("Grace", "Lee",    "grace.lee@newco.org",
                "Marketing",   78500.00);
        Employee henry = new Employee("Henry", "Wilson", "henry.wilson@newco.org",
                "Finance",     91000.00);

        Employee savedFrank = employeeService.saveEmployee(frank);
        Employee savedGrace = employeeService.saveEmployee(grace);
        Employee savedHenry = employeeService.saveEmployee(henry);

        System.out.println("Saved: " + savedFrank);
        System.out.println("Saved: " + savedGrace);
        System.out.println("Saved: " + savedHenry);

        // =================================================================
        // STEP 2: READ ALL – Retrieve every employee from the database
        // =================================================================
        printSection("STEP 2: READ ALL – Fetching all employees (data.sql + saved above)");

        List<Employee> allEmployees = employeeService.getAllEmployees();
        System.out.println("Total employee count: " + allEmployees.size());
        allEmployees.forEach(e -> System.out.println("  → " + e));

        // =================================================================
        // STEP 3: READ BY ID – Retrieve a specific employee
        // =================================================================
        printSection("STEP 3: READ BY ID – Finding employee with ID = 1");

        Optional<Employee> foundEmployee = employeeService.getEmployeeById(1L);
        foundEmployee.ifPresentOrElse(
                e -> System.out.println("Found: " + e),
                () -> System.out.println("No employee found with ID 1")
        );

        // =================================================================
        // STEP 4: FIND BY DEPARTMENT – Custom derived query
        // =================================================================
        printSection("STEP 4: FIND BY DEPARTMENT – Employees in 'Engineering'");

        List<Employee> engineers = employeeService.getEmployeesByDepartment("Engineering");
        System.out.println("Engineers found: " + engineers.size());
        engineers.forEach(e -> System.out.println("  → " + e));

        // =================================================================
        // STEP 5: SEARCH BY NAME – Case-insensitive LIKE query
        // =================================================================
        printSection("STEP 5: SEARCH BY NAME – Employees with 'al' in first name");

        List<Employee> byName = employeeService.searchEmployeesByName("al");
        System.out.println("Matches: " + byName.size());
        byName.forEach(e -> System.out.println("  → " + e));

        // =================================================================
        // STEP 6: FIND BY EMAIL DOMAIN – Custom @Query
        // =================================================================
        printSection("STEP 6: FIND BY EMAIL DOMAIN – Employees at 'techcorp.com'");

        List<Employee> techCorpEmployees = employeeService.getEmployeesByEmailDomain("techcorp.com");
        System.out.println("TechCorp employees: " + techCorpEmployees.size());
        techCorpEmployees.forEach(e -> System.out.println("  → " + e));

        // =================================================================
        // STEP 7: FIND BY SALARY – Employees earning >= 90,000
        // =================================================================
        printSection("STEP 7: SALARY FILTER – Employees earning >= $90,000");

        List<Employee> highEarners = employeeService.getEmployeesBySalaryGreaterThan(90000.0);
        System.out.println("High earners (≥ $90,000): " + highEarners.size());
        highEarners.forEach(e -> System.out.printf("  → %-25s Salary: $%.2f%n",
                e.getFirstName() + " " + e.getLastName(), e.getSalary()));

        // =================================================================
        // STEP 8: UPDATE – Modify an existing employee record
        // =================================================================
        printSection("STEP 8: UPDATE – Promoting Frank Miller to 'Senior Engineering', +$15k");

        Employee updateData = new Employee("Frank", "Miller",
                "frank.miller@newco.org", "Senior Engineering", 120000.00);

        Employee updatedFrank = employeeService.updateEmployee(savedFrank.getId(), updateData);
        System.out.println("Before update → department='Engineering',   salary=$105,000");
        System.out.println("After  update → " + updatedFrank);

        // =================================================================
        // STEP 9: DELETE – Remove an employee record
        // =================================================================
        printSection("STEP 9: DELETE – Removing Henry Wilson (ID=" + savedHenry.getId() + ")");

        long countBefore = employeeService.countEmployees();
        employeeService.deleteEmployee(savedHenry.getId());
        long countAfter  = employeeService.countEmployees();

        System.out.printf("Employee count before deletion: %d%n", countBefore);
        System.out.printf("Employee count after  deletion: %d%n", countAfter);
        System.out.println("Henry Wilson successfully deleted.");

        // =================================================================
        // STEP 10: FINAL STATE – Verify remaining records
        // =================================================================
        printSection("STEP 10: FINAL STATE – All remaining employees");

        employeeService.getAllEmployees()
                .forEach(e -> System.out.println("  → " + e));

        printBanner("DEMO COMPLETE – Application is running. Visit http://localhost:8080/h2-console");
    }

    // =========================================================================
    // Helper – Console Formatting
    // =========================================================================

    /**
     * Prints a prominent banner to the console output.
     *
     * @param title the text to display inside the banner
     */
    private void printBanner(String title) {
        String border = "=".repeat(70);
        System.out.println("\n" + border);
        System.out.printf("  %s%n", title);
        System.out.println(border + "\n");
    }

    /**
     * Prints a section header to clearly separate demo steps in console output.
     *
     * @param title the section title
     */
    private void printSection(String title) {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("  " + title);
        System.out.println("-".repeat(60));
    }
}
