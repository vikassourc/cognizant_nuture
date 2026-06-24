-- ============================================================
-- Exercise 1: Control Structures in Oracle PL/SQL
-- Program  : Cognizant Nurture - Week 1
-- Scenario : Banking System - Customer & Product Management
-- Author   : PL/SQL Practice
-- Date     : 2026
-- ============================================================
-- Topics Covered:
--   1. IF-ELSIF-ELSE Statements
--   2. CASE Statements
--   3. LOOP, WHILE LOOP, FOR LOOP
-- ============================================================

-- Enable server output so DBMS_OUTPUT messages appear
SET SERVEROUTPUT ON SIZE UNLIMITED;

-- ============================================================
-- SECTION 0: DDL - CREATE TABLES AND INSERT SAMPLE DATA
-- ============================================================

-- Drop tables if they already exist (for re-runability)
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE loan_applications CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE products CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE customers CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

-- -----------------------------------------
-- Table: customers
-- Stores customer personal and credit details
-- -----------------------------------------
CREATE TABLE customers (
    customer_id     NUMBER(10)      PRIMARY KEY,
    first_name      VARCHAR2(50)    NOT NULL,
    last_name       VARCHAR2(50)    NOT NULL,
    date_of_birth   DATE            NOT NULL,
    credit_score    NUMBER(3)       CHECK (credit_score BETWEEN 300 AND 900),
    annual_income   NUMBER(12, 2),
    account_type    VARCHAR2(20)    CHECK (account_type IN ('SAVINGS','CURRENT','FIXED')),
    interest_rate   NUMBER(5, 2),  -- preferred interest rate (%)
    age_category    VARCHAR2(20),  -- Young / Middle-Aged / Senior
    created_date    DATE            DEFAULT SYSDATE
);

-- -----------------------------------------
-- Table: products  (bank loan / deposit products)
-- -----------------------------------------
CREATE TABLE products (
    product_id      NUMBER(10)      PRIMARY KEY,
    product_name    VARCHAR2(100)   NOT NULL,
    product_type    VARCHAR2(30)    CHECK (product_type IN ('LOAN','DEPOSIT','INSURANCE')),
    base_price      NUMBER(10, 2)   NOT NULL,
    discounted_price NUMBER(10, 2),
    discount_pct    NUMBER(5, 2)   DEFAULT 0
);

-- -----------------------------------------
-- Table: loan_applications
-- -----------------------------------------
CREATE TABLE loan_applications (
    application_id  NUMBER(10)      PRIMARY KEY,
    customer_id     NUMBER(10)      REFERENCES customers(customer_id),
    loan_amount     NUMBER(12, 2)   NOT NULL,
    loan_type       VARCHAR2(30),
    eligibility     VARCHAR2(20),  -- APPROVED / REJECTED / REVIEW
    remarks         VARCHAR2(200)
);

-- -----------------------------------------
-- Insert Sample Customers
-- -----------------------------------------
INSERT INTO customers (customer_id, first_name, last_name, date_of_birth, credit_score, annual_income, account_type)
VALUES (101, 'Aarav',   'Sharma',   TO_DATE('1998-05-14','YYYY-MM-DD'), 720, 480000,  'SAVINGS');

INSERT INTO customers (customer_id, first_name, last_name, date_of_birth, credit_score, annual_income, account_type)
VALUES (102, 'Priya',   'Mehta',    TO_DATE('1985-11-22','YYYY-MM-DD'), 650, 920000,  'CURRENT');

INSERT INTO customers (customer_id, first_name, last_name, date_of_birth, credit_score, annual_income, account_type)
VALUES (103, 'Rajan',   'Iyer',     TO_DATE('1960-03-30','YYYY-MM-DD'), 780, 1500000, 'FIXED');

INSERT INTO customers (customer_id, first_name, last_name, date_of_birth, credit_score, annual_income, account_type)
VALUES (104, 'Sneha',   'Kapoor',   TO_DATE('2000-07-19','YYYY-MM-DD'), 490, 300000,  'SAVINGS');

INSERT INTO customers (customer_id, first_name, last_name, date_of_birth, credit_score, annual_income, account_type)
VALUES (105, 'Vikram',  'Nair',     TO_DATE('1955-09-05','YYYY-MM-DD'), 820, 2500000, 'FIXED');

INSERT INTO customers (customer_id, first_name, last_name, date_of_birth, credit_score, annual_income, account_type)
VALUES (106, 'Anjali',  'Verma',    TO_DATE('1992-01-28','YYYY-MM-DD'), 560, 600000,  'CURRENT');

-- -----------------------------------------
-- Insert Sample Products
-- -----------------------------------------
INSERT INTO products (product_id, product_name, product_type, base_price)
VALUES (1, 'Home Loan Premium',      'LOAN',      500000);

INSERT INTO products (product_id, product_name, product_type, base_price)
VALUES (2, 'Personal Loan Flexi',    'LOAN',      200000);

INSERT INTO products (product_id, product_name, product_type, base_price)
VALUES (3, 'Fixed Deposit 1 Year',   'DEPOSIT',   100000);

INSERT INTO products (product_id, product_name, product_type, base_price)
VALUES (4, 'Life Insurance Basic',   'INSURANCE',  12000);

INSERT INTO products (product_id, product_name, product_type, base_price)
VALUES (5, 'Car Loan Standard',      'LOAN',      300000);

-- -----------------------------------------
-- Insert Sample Loan Applications
-- -----------------------------------------
INSERT INTO loan_applications (application_id, customer_id, loan_amount, loan_type)
VALUES (1001, 101, 1000000, 'HOME LOAN');

INSERT INTO loan_applications (application_id, customer_id, loan_amount, loan_type)
VALUES (1002, 102, 500000,  'PERSONAL LOAN');

INSERT INTO loan_applications (application_id, customer_id, loan_amount, loan_type)
VALUES (1003, 103, 2000000, 'HOME LOAN');

INSERT INTO loan_applications (application_id, customer_id, loan_amount, loan_type)
VALUES (1004, 104, 250000,  'PERSONAL LOAN');

INSERT INTO loan_applications (application_id, customer_id, loan_amount, loan_type)
VALUES (1005, 105, 5000000, 'HOME LOAN');

INSERT INTO loan_applications (application_id, customer_id, loan_amount, loan_type)
VALUES (1006, 106, 750000,  'CAR LOAN');

COMMIT;

DBMS_OUTPUT.PUT_LINE('=== Tables created and sample data inserted successfully. ===');

-- ============================================================
-- SECTION 1: IF-ELSIF-ELSE STATEMENT
-- Scenario : Categorise each customer by age and assign a
--            preferred interest rate for their account type.
-- ============================================================
DECLARE
    -- Cursor to fetch all customers
    CURSOR c_customers IS
        SELECT customer_id,
               first_name || ' ' || last_name  AS full_name,
               TRUNC(MONTHS_BETWEEN(SYSDATE, date_of_birth) / 12) AS age,
               account_type
        FROM customers
        ORDER BY customer_id;

    v_interest_rate  NUMBER(5, 2);
    v_age_category   VARCHAR2(20);
BEGIN
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('============================================================');
    DBMS_OUTPUT.PUT_LINE('  SECTION 1 : IF-ELSIF-ELSE - Customer Age Categorisation  ');
    DBMS_OUTPUT.PUT_LINE('============================================================');
    DBMS_OUTPUT.PUT_LINE(RPAD('Customer',25) || RPAD('Age',6) ||
                         RPAD('Category',15) || 'Interest Rate (%)');
    DBMS_OUTPUT.PUT_LINE(RPAD('-',25,'-') || RPAD('-',6,'-') ||
                         RPAD('-',15,'-') || RPAD('-',18,'-'));

    FOR rec IN c_customers LOOP
        -- -------------------------------------------------------
        -- IF-ELSIF-ELSE: Categorise by age and assign rate
        -- -------------------------------------------------------
        IF rec.age < 30 THEN
            -- Young customers get a lower entry-level savings rate
            v_age_category  := 'Young';
            v_interest_rate := 3.50;

        ELSIF rec.age BETWEEN 30 AND 60 THEN
            -- Middle-Aged customers get a standard balanced rate
            v_age_category  := 'Middle-Aged';
            v_interest_rate := 5.00;

        ELSE
            -- Senior customers (> 60) get a higher preferential rate
            v_age_category  := 'Senior';
            v_interest_rate := 6.75;

        END IF;

        -- Further adjust rate based on account type using nested IF
        IF rec.account_type = 'FIXED' THEN
            v_interest_rate := v_interest_rate + 1.25;  -- bonus for fixed deposits
        ELSIF rec.account_type = 'CURRENT' THEN
            v_interest_rate := v_interest_rate - 0.50;  -- lower for current accounts
        END IF;

        -- Persist computed values back to the table
        UPDATE customers
        SET    interest_rate = v_interest_rate,
               age_category  = v_age_category
        WHERE  customer_id   = rec.customer_id;

        DBMS_OUTPUT.PUT_LINE(
            RPAD(rec.full_name, 25) ||
            RPAD(rec.age,       6)  ||
            RPAD(v_age_category,15) ||
            v_interest_rate || '%'
        );
    END LOOP;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('Customer interest rates updated successfully.');

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('ERROR: No customer records found.');
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('ERROR in Section 1: ' || SQLERRM);
END;
/

-- ============================================================
-- SECTION 2: CASE STATEMENT
-- Scenario : Determine loan eligibility based on credit score
--            and update the loan_applications table.
-- ============================================================
DECLARE
    CURSOR c_applications IS
        SELECT la.application_id,
               la.customer_id,
               c.first_name || ' ' || c.last_name AS full_name,
               c.credit_score,
               la.loan_amount,
               la.loan_type
        FROM   loan_applications la
        JOIN   customers         c  ON c.customer_id = la.customer_id
        ORDER  BY la.application_id;

    v_eligibility   VARCHAR2(20);
    v_remarks       VARCHAR2(200);
BEGIN
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('============================================================');
    DBMS_OUTPUT.PUT_LINE('  SECTION 2 : CASE Statement - Loan Eligibility Assessment ');
    DBMS_OUTPUT.PUT_LINE('============================================================');
    DBMS_OUTPUT.PUT_LINE(RPAD('App ID', 8) || RPAD('Customer',25) ||
                         RPAD('Score',7)   || RPAD('Decision',12) || 'Remarks');
    DBMS_OUTPUT.PUT_LINE(RPAD('-',8,'-')   || RPAD('-',25,'-')    ||
                         RPAD('-',7,'-')   || RPAD('-',12,'-')    || RPAD('-',30,'-'));

    FOR rec IN c_applications LOOP
        -- -------------------------------------------------------
        -- CASE Statement: Classify loan eligibility by credit score
        -- Credit scoring bands (typical industry standard):
        --   >= 750 : Excellent  -> Approved
        --   700-749: Good       -> Approved with standard terms
        --   650-699: Fair       -> Under review / conditional
        --   600-649: Poor       -> Conditional / higher rate
        --   < 600  : Very Poor  -> Rejected
        -- -------------------------------------------------------
        v_eligibility :=
            CASE
                WHEN rec.credit_score >= 750 THEN 'APPROVED'
                WHEN rec.credit_score >= 700 THEN 'APPROVED'
                WHEN rec.credit_score >= 650 THEN 'REVIEW'
                WHEN rec.credit_score >= 600 THEN 'REVIEW'
                ELSE                              'REJECTED'
            END;

        -- Searched CASE for generating remarks
        v_remarks :=
            CASE
                WHEN rec.credit_score >= 750 THEN
                    'Excellent credit. Best rate offered.'
                WHEN rec.credit_score >= 700 THEN
                    'Good credit. Standard interest rate.'
                WHEN rec.credit_score >= 650 THEN
                    'Fair credit. Conditional approval - submit income proof.'
                WHEN rec.credit_score >= 600 THEN
                    'Poor credit. Higher interest rate applies.'
                ELSE
                    'Very poor credit. Loan rejected. Improve credit score.'
            END;

        -- Update the loan_applications table
        UPDATE loan_applications
        SET    eligibility = v_eligibility,
               remarks     = v_remarks
        WHERE  application_id = rec.application_id;

        DBMS_OUTPUT.PUT_LINE(
            RPAD(rec.application_id, 8) ||
            RPAD(rec.full_name,      25) ||
            RPAD(rec.credit_score,   7)  ||
            RPAD(v_eligibility,      12) ||
            v_remarks
        );
    END LOOP;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('Loan eligibility assessments completed and saved.');

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('ERROR: No loan applications found.');
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('ERROR in Section 2: ' || SQLERRM);
END;
/

-- ============================================================
-- SECTION 3A: BASIC LOOP
-- Scenario : Apply a cumulative 2% discount per iteration
--            on a selected loan product (up to 5 iterations)
--            and display how the discounted price evolves.
-- ============================================================
DECLARE
    v_product_id      products.product_id%TYPE     := 1;  -- Home Loan Premium
    v_product_name    products.product_name%TYPE;
    v_base_price      products.base_price%TYPE;
    v_current_price   products.base_price%TYPE;
    v_discount_step   NUMBER := 2;      -- 2% discount per loop iteration
    v_max_iterations  NUMBER := 5;      -- maximum loop iterations
    v_counter         NUMBER := 1;      -- loop counter
    v_total_discount  NUMBER := 0;
BEGIN
    -- Fetch product details
    SELECT product_name, base_price
    INTO   v_product_name, v_base_price
    FROM   products
    WHERE  product_id = v_product_id;

    v_current_price := v_base_price;

    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('============================================================');
    DBMS_OUTPUT.PUT_LINE('  SECTION 3A: BASIC LOOP - Iterative Discount Application  ');
    DBMS_OUTPUT.PUT_LINE('============================================================');
    DBMS_OUTPUT.PUT_LINE('Product  : ' || v_product_name);
    DBMS_OUTPUT.PUT_LINE('Base Price: INR ' || TO_CHAR(v_base_price, '99,99,999.00'));
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE(RPAD('Iteration', 12) || RPAD('Discount%', 12) ||
                         RPAD('Discounted Price', 20) || 'Saving (INR)');
    DBMS_OUTPUT.PUT_LINE(RPAD('-',12,'-') || RPAD('-',12,'-') ||
                         RPAD('-',20,'-') || RPAD('-',15,'-'));

    -- -------------------------------------------------------
    -- BASIC (INFINITE) LOOP with EXIT WHEN condition
    -- -------------------------------------------------------
    LOOP
        -- Apply cumulative discount step
        v_total_discount  := v_counter * v_discount_step;
        v_current_price   := v_base_price * (1 - v_total_discount / 100);

        DBMS_OUTPUT.PUT_LINE(
            RPAD(v_counter,          12) ||
            RPAD(v_total_discount || '%', 12) ||
            RPAD('INR ' || TO_CHAR(v_current_price, '99,99,999.00'), 20) ||
            'INR ' || TO_CHAR(v_base_price - v_current_price, '99,99,999.00')
        );

        -- Exit condition: stop when max iterations reached
        EXIT WHEN v_counter >= v_max_iterations;

        v_counter := v_counter + 1;
    END LOOP;

    -- Save the final discounted price back to the products table
    UPDATE products
    SET    discounted_price = v_current_price,
           discount_pct     = v_total_discount
    WHERE  product_id       = v_product_id;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('Final discounted price saved: INR ' ||
                          TO_CHAR(v_current_price, '99,99,999.00') ||
                          ' (' || v_total_discount || '% off)');

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('ERROR: Product ID ' || v_product_id || ' not found.');
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('ERROR in Section 3A: ' || SQLERRM);
END;
/

-- ============================================================
-- SECTION 3B: WHILE LOOP
-- Scenario : Simulate compound interest growth on a Fixed
--            Deposit until the balance doubles (Rule of 72).
-- ============================================================
DECLARE
    v_principal      NUMBER := 100000;   -- Starting principal: INR 1,00,000
    v_annual_rate    NUMBER := 7.5;      -- Annual interest rate: 7.5%
    v_target_balance NUMBER;             -- Balance at which to stop (double)
    v_balance        NUMBER;
    v_year           NUMBER := 0;
    v_interest       NUMBER;
BEGIN
    v_balance        := v_principal;
    v_target_balance := v_principal * 2;  -- Stop when balance doubles

    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('============================================================');
    DBMS_OUTPUT.PUT_LINE('  SECTION 3B: WHILE LOOP - Fixed Deposit Compound Growth   ');
    DBMS_OUTPUT.PUT_LINE('============================================================');
    DBMS_OUTPUT.PUT_LINE('Principal      : INR ' || TO_CHAR(v_principal,      '99,99,999.00'));
    DBMS_OUTPUT.PUT_LINE('Annual Rate    : '     || v_annual_rate || '%');
    DBMS_OUTPUT.PUT_LINE('Target Balance : INR ' || TO_CHAR(v_target_balance, '99,99,999.00'));
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE(RPAD('Year', 8) || RPAD('Interest Earned', 20) || 'Balance (INR)');
    DBMS_OUTPUT.PUT_LINE(RPAD('-',8,'-') || RPAD('-',20,'-') || RPAD('-',20,'-'));

    -- -------------------------------------------------------
    -- WHILE LOOP: Continue compounding until balance doubles
    -- -------------------------------------------------------
    WHILE v_balance < v_target_balance LOOP
        v_year     := v_year + 1;
        v_interest := ROUND(v_balance * v_annual_rate / 100, 2);
        v_balance  := v_balance + v_interest;

        DBMS_OUTPUT.PUT_LINE(
            RPAD(v_year,    8) ||
            RPAD('INR ' || TO_CHAR(v_interest, '99,99,999.00'), 20) ||
            'INR ' || TO_CHAR(v_balance, '99,99,999.00')
        );
    END LOOP;

    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('Investment doubled in ' || v_year || ' years.');
    DBMS_OUTPUT.PUT_LINE('Final Balance: INR ' || TO_CHAR(v_balance, '99,99,999.00'));

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('ERROR in Section 3B: ' || SQLERRM);
END;
/

-- ============================================================
-- SECTION 3C: FOR LOOP
-- Scenario : Apply a tiered discount to all 5 products based
--            on a fixed percentage mapped by product index,
--            then display a summary of all product prices.
-- ============================================================
DECLARE
    -- Array of discount percentages for each product (1-5)
    TYPE t_discount_array IS TABLE OF NUMBER INDEX BY PLS_INTEGER;
    v_discounts   t_discount_array;

    v_product_id   NUMBER;
    v_product_name products.product_name%TYPE;
    v_base_price   products.base_price%TYPE;
    v_disc_price   NUMBER;
    v_disc_pct     NUMBER;
BEGIN
    -- Define a tiered discount for each product index
    v_discounts(1) := 10;   -- Product 1: 10% discount
    v_discounts(2) := 8;    -- Product 2: 8% discount
    v_discounts(3) := 5;    -- Product 3: 5% discount
    v_discounts(4) := 12;   -- Product 4: 12% discount
    v_discounts(5) := 7;    -- Product 5: 7% discount

    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('============================================================');
    DBMS_OUTPUT.PUT_LINE('  SECTION 3C: FOR LOOP - Tiered Product Discount Summary   ');
    DBMS_OUTPUT.PUT_LINE('============================================================');
    DBMS_OUTPUT.PUT_LINE(RPAD('ID',4) || RPAD('Product Name',30) ||
                         RPAD('Base Price',15) || RPAD('Disc%',7) || 'New Price');
    DBMS_OUTPUT.PUT_LINE(RPAD('-',4,'-') || RPAD('-',30,'-') ||
                         RPAD('-',15,'-') || RPAD('-',7,'-') || RPAD('-',15,'-'));

    -- -------------------------------------------------------
    -- FOR LOOP: Iterate product IDs 1 through 5
    -- -------------------------------------------------------
    FOR i IN 1 .. 5 LOOP
        v_disc_pct := v_discounts(i);

        -- Fetch product details for current loop index
        SELECT product_id, product_name, base_price
        INTO   v_product_id, v_product_name, v_base_price
        FROM   products
        WHERE  product_id = i;

        -- Calculate new discounted price
        v_disc_price := ROUND(v_base_price * (1 - v_disc_pct / 100), 2);

        -- Update the products table
        UPDATE products
        SET    discounted_price = v_disc_price,
               discount_pct    = v_disc_pct
        WHERE  product_id       = i;

        DBMS_OUTPUT.PUT_LINE(
            RPAD(v_product_id,   4)  ||
            RPAD(v_product_name, 30) ||
            RPAD('INR ' || TO_CHAR(v_base_price,  '9,99,999'), 15) ||
            RPAD(v_disc_pct || '%', 7) ||
            'INR ' || TO_CHAR(v_disc_price, '9,99,999')
        );
    END LOOP;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('All product discounts applied and saved to database.');

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('ERROR: A product record was not found during FOR LOOP.');
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('ERROR in Section 3C: ' || SQLERRM);
END;
/

-- ============================================================
-- VERIFICATION QUERIES
-- Run these SELECT statements to verify results.
-- ============================================================

-- Verify customer age categorisation and interest rates
SELECT customer_id,
       first_name || ' ' || last_name AS customer_name,
       TRUNC(MONTHS_BETWEEN(SYSDATE, date_of_birth) / 12) AS age,
       age_category,
       account_type,
       interest_rate
FROM   customers
ORDER  BY customer_id;

-- Verify loan eligibility decisions
SELECT la.application_id,
       c.first_name || ' ' || c.last_name AS customer_name,
       c.credit_score,
       la.loan_type,
       la.eligibility,
       la.remarks
FROM   loan_applications la
JOIN   customers         c  ON c.customer_id = la.customer_id
ORDER  BY la.application_id;

-- Verify product discount updates
SELECT product_id,
       product_name,
       product_type,
       base_price,
       discount_pct,
       discounted_price
FROM   products
ORDER  BY product_id;

-- ============================================================
-- END OF EXERCISE 1 : CONTROL STRUCTURES
-- ============================================================
