-- =============================================================================
-- Sample Data - Employees
-- Cognizant Nurture - Week 2 | Spring Data JPA Quick Example
--
-- This file is executed by Spring Boot after Hibernate creates the schema
-- (ddl-auto=create-drop). The table name 'employees' matches @Table(name="employees")
-- in the Employee entity.
-- =============================================================================

-- Engineering Department
INSERT INTO employees (first_name, last_name, email, department, salary)
VALUES ('Alice', 'Johnson', 'alice.johnson@techcorp.com', 'Engineering', 95000.00);

INSERT INTO employees (first_name, last_name, email, department, salary)
VALUES ('Bob', 'Smith', 'bob.smith@techcorp.com', 'Engineering', 88000.00);

-- Marketing Department
INSERT INTO employees (first_name, last_name, email, department, salary)
VALUES ('Carol', 'Williams', 'carol.williams@techcorp.com', 'Marketing', 72000.00);

-- Finance Department
INSERT INTO employees (first_name, last_name, email, department, salary)
VALUES ('David', 'Brown', 'david.brown@techcorp.com', 'Finance', 81000.00);

-- Human Resources Department
INSERT INTO employees (first_name, last_name, email, department, salary)
VALUES ('Eva', 'Davis', 'eva.davis@techcorp.com', 'Human Resources', 67000.00);
