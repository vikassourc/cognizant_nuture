-- ============================================================
-- Exercise 3: Stored Procedures in Oracle PL/SQL
-- Program  : Cognizant Nurture - Week 1
-- Scenario : Banking & HR Financial System
-- Author   : PL/SQL Practice
-- Date     : 2026
-- ============================================================
-- Procedures Covered:
--   1. ProcessMonthlyInterest  - Calculate and apply monthly interest
--   2. UpdateEmployeeBonus     - Update salaries by department
--   3. TransferFunds           - Transfer money between accounts
-- ============================================================

-- Enable server output so DBMS_OUTPUT messages appear
SET SERVEROUTPUT ON SIZE UNLIMITED;

-- ============================================================
-- SECTION 0: DDL - CREATE TABLES AND INSERT SAMPLE DATA
-- ============================================================

-- Drop tables if they already exist (for re-runability)
BEGIN EXECUTE IMMEDIATE 'DROP TABLE transaction_log      CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN EXECUTE IMMEDIATE 'DROP TABLE accounts             CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN EXECUTE IMMEDIATE 'DROP TABLE employees            CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN EXECUTE IMMEDIATE 'DROP TABLE departments          CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN NULL; END;
/

-- -----------------------------------------
-- Table: departments
-- -----------------------------------------
CREATE TABLE departments (
    department_id   NUMBER(5)       PRIMARY KEY,
    dept_name       VARCHAR2(50)    NOT NULL,
    location        VARCHAR2(50),
    budget          NUMBER(15, 2)
);

-- -----------------------------------------
-- Table: employees
-- Stores employee records linked to departments
-- -----------------------------------------
CREATE TABLE employees (
    employee_id     NUMBER(10)      PRIMARY KEY,
    first_name      VARCHAR2(50)    NOT NULL,
    last_name       VARCHAR2(50)    NOT NULL,
    department_id   NUMBER(5)       REFERENCES departments(department_id),
    designation     VARCHAR2(80),
    salary          NUMBER(12, 2)   NOT NULL,
    hire_date       DATE,
    email           VARCHAR2(100)
);

-- -----------------------------------------
-- Table: accounts
-- Stores bank account details for customers
-- -----------------------------------------
CREATE TABLE accounts (
    account_id      NUMBER(10)      PRIMARY KEY,
    account_number  VARCHAR2(20)    UNIQUE NOT NULL,
    account_holder  VARCHAR2(100)   NOT NULL,
    account_type    VARCHAR2(20)    CHECK (account_type IN ('SAVINGS','CURRENT','FIXED')),
    balance         NUMBER(15, 2)   DEFAULT 0 CHECK (balance >= 0),
    interest_rate   NUMBER(5, 2)    DEFAULT 0,  -- annual rate %
    status          VARCHAR2(10)    DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE','FROZEN','CLOSED')),
    last_updated    DATE            DEFAULT SYSDATE
);

-- -----------------------------------------
-- Table: transaction_log
-- Audit trail for all banking transactions
-- -----------------------------------------
CREATE TABLE transaction_log (
    log_id              NUMBER(15)      PRIMARY KEY,
    account_id          NUMBER(10)      REFERENCES accounts(account_id),
    transaction_type    VARCHAR2(30)    NOT NULL,
    amount              NUMBER(15, 2)   NOT NULL,
    old_balance         NUMBER(15, 2),
    new_balance         NUMBER(15, 2),
    reference_id        VARCHAR2(50),   -- counterpart account or reference
    remarks             VARCHAR2(200),
    transaction_date    DATE            DEFAULT SYSDATE,
    performed_by        VARCHAR2(50)    DEFAULT USER
);

-- Sequence to auto-generate log IDs
CREATE SEQUENCE seq_log_id START WITH 1 INCREMENT BY 1 NOCACHE;

-- -----------------------------------------
-- Insert Sample Departments
-- -----------------------------------------
INSERT INTO departments VALUES (10, 'Information Technology', 'Bangalore',  5000000);
INSERT INTO departments VALUES (20, 'Risk & Compliance',      'Mumbai',     3000000);
INSERT INTO departments VALUES (30, 'Retail Banking',         'Delhi',      4500000);
INSERT INTO departments VALUES (40, 'Human Resources',        'Hyderabad',  1500000);

-- -----------------------------------------
-- Insert Sample Employees
-- -----------------------------------------
INSERT INTO employees VALUES (1001, 'Arjun',   'Rao',      10, 'Senior Developer',       85000,  TO_DATE('2020-06-15','YYYY-MM-DD'), 'arjun.rao@bank.com');
INSERT INTO employees VALUES (1002, 'Meera',   'Pillai',   10, 'Database Administrator',  92000,  TO_DATE('2019-03-10','YYYY-MM-DD'), 'meera.pillai@bank.com');
INSERT INTO employees VALUES (1003, 'Kiran',   'Joshi',    10, 'Junior Developer',        55000,  TO_DATE('2023-01-20','YYYY-MM-DD'), 'kiran.joshi@bank.com');
INSERT INTO employees VALUES (1004, 'Sunita',  'Deshpande',20, 'Compliance Manager',      110000, TO_DATE('2017-08-01','YYYY-MM-DD'), 'sunita.d@bank.com');
INSERT INTO employees VALUES (1005, 'Rohit',   'Kulkarni', 20, 'Risk Analyst',             78000,  TO_DATE('2021-11-05','YYYY-MM-DD'), 'rohit.k@bank.com');
INSERT INTO employees VALUES (1006, 'Deepa',   'Menon',    30, 'Branch Manager',          125000, TO_DATE('2015-04-22','YYYY-MM-DD'), 'deepa.m@bank.com');
INSERT INTO employees VALUES (1007, 'Suresh',  'Patil',    30, 'Relationship Manager',     68000,  TO_DATE('2022-07-13','YYYY-MM-DD'), 'suresh.p@bank.com');
INSERT INTO employees VALUES (1008, 'Kavitha', 'Nambiar',  40, 'HR Manager',              95000,  TO_DATE('2018-09-30','YYYY-MM-DD'), 'kavitha.n@bank.com');

-- -----------------------------------------
-- Insert Sample Accounts
-- -----------------------------------------
INSERT INTO accounts VALUES (2001, 'SB-100201', 'Aarav Sharma',   'SAVINGS', 250000.00, 4.00, 'ACTIVE', SYSDATE);
INSERT INTO accounts VALUES (2002, 'SB-100202', 'Priya Mehta',    'SAVINGS', 180000.00, 4.00, 'ACTIVE', SYSDATE);
INSERT INTO accounts VALUES (2003, 'CA-200301', 'Rajan Iyer',     'CURRENT', 750000.00, 2.50, 'ACTIVE', SYSDATE);
INSERT INTO accounts VALUES (2004, 'FD-300401', 'Sneha Kapoor',   'FIXED',   500000.00, 7.50, 'ACTIVE', SYSDATE);
INSERT INTO accounts VALUES (2005, 'SB-100205', 'Vikram Nair',    'SAVINGS', 125000.00, 4.00, 'ACTIVE', SYSDATE);
INSERT INTO accounts VALUES (2006, 'CA-200306', 'Anjali Verma',   'CURRENT', 320000.00, 2.50, 'FROZEN', SYSDATE);

COMMIT;

DBMS_OUTPUT.PUT_LINE('=== Schema created and sample data loaded successfully. ===');

-- ============================================================
-- PROCEDURE 1: ProcessMonthlyInterest
-- ============================================================
-- Purpose  : Calculates monthly interest on an account balance
--            and credits the interest amount to the account.
-- Params   : p_account_id    IN  - Account to process
--            p_annual_rate   IN  - Annual interest rate (%)
--                                  (uses account's own rate if 0)
--            p_interest_amt  OUT - Computed interest amount
--            p_new_balance   OUT - Updated balance after interest
--            p_status        OUT - 'SUCCESS' or 'FAILED'
-- ============================================================
CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest (
    p_account_id    IN   accounts.account_id%TYPE,
    p_annual_rate   IN   NUMBER DEFAULT 0,         -- 0 = use account's stored rate
    p_interest_amt  OUT  NUMBER,
    p_new_balance   OUT  NUMBER,
    p_status        OUT  VARCHAR2
)
AS
    -- Local variables
    v_balance       accounts.balance%TYPE;
    v_rate          accounts.interest_rate%TYPE;
    v_acc_status    accounts.status%TYPE;
    v_account_no    accounts.account_number%TYPE;
    v_monthly_rate  NUMBER;
    v_old_balance   NUMBER;

    -- Custom exceptions
    e_account_not_found  EXCEPTION;
    e_account_frozen     EXCEPTION;
    e_negative_balance   EXCEPTION;

BEGIN
    -- -------------------------------------------------------
    -- Step 1: Fetch current account details
    -- -------------------------------------------------------
    BEGIN
        SELECT balance, interest_rate, status, account_number
        INTO   v_balance, v_rate, v_acc_status, v_account_no
        FROM   accounts
        WHERE  account_id = p_account_id;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE e_account_not_found;
    END;

    -- -------------------------------------------------------
    -- Step 2: Business validations
    -- -------------------------------------------------------
    IF v_acc_status != 'ACTIVE' THEN
        RAISE e_account_frozen;
    END IF;

    IF v_balance < 0 THEN
        RAISE e_negative_balance;
    END IF;

    -- -------------------------------------------------------
    -- Step 3: Use provided rate or fall back to stored rate
    -- -------------------------------------------------------
    v_rate := CASE WHEN p_annual_rate > 0 THEN p_annual_rate
                   ELSE v_rate
              END;

    -- Convert annual rate to monthly rate
    v_monthly_rate := v_rate / 12 / 100;

    -- -------------------------------------------------------
    -- Step 4: Calculate monthly interest
    -- -------------------------------------------------------
    v_old_balance  := v_balance;
    p_interest_amt := ROUND(v_balance * v_monthly_rate, 2);
    p_new_balance  := v_balance + p_interest_amt;

    -- -------------------------------------------------------
    -- Step 5: Update account balance and timestamp
    -- -------------------------------------------------------
    UPDATE accounts
    SET    balance      = p_new_balance,
           last_updated = SYSDATE
    WHERE  account_id   = p_account_id;

    -- -------------------------------------------------------
    -- Step 6: Log the transaction for audit trail
    -- -------------------------------------------------------
    INSERT INTO transaction_log (
        log_id, account_id, transaction_type, amount,
        old_balance, new_balance, reference_id, remarks
    ) VALUES (
        seq_log_id.NEXTVAL,
        p_account_id,
        'MONTHLY_INTEREST',
        p_interest_amt,
        v_old_balance,
        p_new_balance,
        'RATE-' || TO_CHAR(v_rate, 'FM99.99') || '%',
        'Monthly interest credited at ' || v_rate || '% p.a. (monthly rate: ' ||
        ROUND(v_monthly_rate * 100, 4) || '%)'
    );

    COMMIT;
    p_status := 'SUCCESS';

    DBMS_OUTPUT.PUT_LINE('[ProcessMonthlyInterest] Account: ' || v_account_no ||
                         ' | Interest: INR ' || p_interest_amt ||
                         ' | New Balance: INR ' || p_new_balance);

EXCEPTION
    WHEN e_account_not_found THEN
        ROLLBACK;
        p_status       := 'FAILED';
        p_interest_amt := 0;
        p_new_balance  := 0;
        DBMS_OUTPUT.PUT_LINE('[ProcessMonthlyInterest] ERROR: Account ID ' ||
                              p_account_id || ' not found.');

    WHEN e_account_frozen THEN
        ROLLBACK;
        p_status       := 'FAILED';
        p_interest_amt := 0;
        p_new_balance  := 0;
        DBMS_OUTPUT.PUT_LINE('[ProcessMonthlyInterest] ERROR: Account ' ||
                              v_account_no || ' is ' || v_acc_status ||
                              '. Interest processing skipped.');

    WHEN e_negative_balance THEN
        ROLLBACK;
        p_status       := 'FAILED';
        p_interest_amt := 0;
        p_new_balance  := 0;
        DBMS_OUTPUT.PUT_LINE('[ProcessMonthlyInterest] ERROR: Negative balance on account ' ||
                              v_account_no || '. Cannot apply interest.');

    WHEN OTHERS THEN
        ROLLBACK;
        p_status       := 'FAILED';
        p_interest_amt := 0;
        p_new_balance  := 0;
        DBMS_OUTPUT.PUT_LINE('[ProcessMonthlyInterest] UNEXPECTED ERROR: ' || SQLERRM);
END ProcessMonthlyInterest;
/

-- ============================================================
-- PROCEDURE 2: UpdateEmployeeBonus
-- ============================================================
-- Purpose  : Updates the salary of all employees in a given
--            department by adding a bonus percentage.
-- Params   : p_department_id   IN  - Department to update
--            p_bonus_pct       IN  - Bonus percentage (e.g. 10 = 10%)
--            p_emp_count       OUT - Number of employees updated
--            p_total_bonus     OUT - Total bonus amount disbursed
--            p_status          OUT - 'SUCCESS' or 'FAILED'
-- ============================================================
CREATE OR REPLACE PROCEDURE UpdateEmployeeBonus (
    p_department_id  IN   employees.department_id%TYPE,
    p_bonus_pct      IN   NUMBER,
    p_emp_count      OUT  NUMBER,
    p_total_bonus    OUT  NUMBER,
    p_status         OUT  VARCHAR2
)
AS
    v_dept_name      departments.dept_name%TYPE;
    v_bonus_amount   NUMBER;

    -- Cursor to iterate over employees in the department
    CURSOR c_emp IS
        SELECT employee_id, first_name || ' ' || last_name AS full_name, salary
        FROM   employees
        WHERE  department_id = p_department_id
        FOR UPDATE OF salary;  -- lock rows for update

    -- Custom exceptions
    e_dept_not_found    EXCEPTION;
    e_invalid_bonus_pct EXCEPTION;
    e_no_employees      EXCEPTION;

BEGIN
    -- -------------------------------------------------------
    -- Step 1: Validate department exists
    -- -------------------------------------------------------
    BEGIN
        SELECT dept_name INTO v_dept_name
        FROM   departments
        WHERE  department_id = p_department_id;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE e_dept_not_found;
    END;

    -- -------------------------------------------------------
    -- Step 2: Validate bonus percentage
    -- -------------------------------------------------------
    IF p_bonus_pct <= 0 OR p_bonus_pct > 50 THEN
        -- Bonus must be between 0.01% and 50%
        RAISE e_invalid_bonus_pct;
    END IF;

    p_emp_count  := 0;
    p_total_bonus := 0;

    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('[UpdateEmployeeBonus] Department: ' || v_dept_name ||
                         ' | Bonus: ' || p_bonus_pct || '%');
    DBMS_OUTPUT.PUT_LINE(RPAD('Employee',30) || RPAD('Old Salary',15) ||
                         RPAD('Bonus Amt',15) || 'New Salary');
    DBMS_OUTPUT.PUT_LINE(RPAD('-',30,'-') || RPAD('-',15,'-') ||
                         RPAD('-',15,'-') || RPAD('-',15,'-'));

    -- -------------------------------------------------------
    -- Step 3: Loop through each employee and update salary
    -- -------------------------------------------------------
    FOR emp_rec IN c_emp LOOP
        v_bonus_amount := ROUND(emp_rec.salary * p_bonus_pct / 100, 2);

        -- Apply bonus to employee salary using cursor FOR UPDATE
        UPDATE employees
        SET    salary       = salary + v_bonus_amount
        WHERE  employee_id  = emp_rec.employee_id;

        p_emp_count   := p_emp_count  + 1;
        p_total_bonus := p_total_bonus + v_bonus_amount;

        DBMS_OUTPUT.PUT_LINE(
            RPAD(emp_rec.full_name,  30) ||
            RPAD('INR ' || TO_CHAR(emp_rec.salary,          'FM99,99,999'), 15) ||
            RPAD('INR ' || TO_CHAR(v_bonus_amount,          'FM99,99,999'), 15) ||
            'INR ' || TO_CHAR(emp_rec.salary + v_bonus_amount, 'FM99,99,999')
        );
    END LOOP;

    -- -------------------------------------------------------
    -- Step 4: Check if any employees were actually updated
    -- -------------------------------------------------------
    IF p_emp_count = 0 THEN
        RAISE e_no_employees;
    END IF;

    COMMIT;
    p_status := 'SUCCESS';

    DBMS_OUTPUT.PUT_LINE(RPAD('-',75,'-'));
    DBMS_OUTPUT.PUT_LINE('[UpdateEmployeeBonus] SUCCESS: ' || p_emp_count ||
                         ' employees updated. Total bonus disbursed: INR ' ||
                         TO_CHAR(p_total_bonus, 'FM99,99,999.00'));

EXCEPTION
    WHEN e_dept_not_found THEN
        ROLLBACK;
        p_status      := 'FAILED';
        p_emp_count   := 0;
        p_total_bonus := 0;
        DBMS_OUTPUT.PUT_LINE('[UpdateEmployeeBonus] ERROR: Department ID ' ||
                              p_department_id || ' does not exist.');

    WHEN e_invalid_bonus_pct THEN
        ROLLBACK;
        p_status      := 'FAILED';
        p_emp_count   := 0;
        p_total_bonus := 0;
        DBMS_OUTPUT.PUT_LINE('[UpdateEmployeeBonus] ERROR: Bonus percentage ' ||
                              p_bonus_pct || '% is invalid. Must be 0.01 to 50.');

    WHEN e_no_employees THEN
        ROLLBACK;
        p_status      := 'FAILED';
        p_emp_count   := 0;
        p_total_bonus := 0;
        DBMS_OUTPUT.PUT_LINE('[UpdateEmployeeBonus] ERROR: No employees found in department ' ||
                              p_department_id || '.');

    WHEN OTHERS THEN
        ROLLBACK;
        p_status      := 'FAILED';
        p_emp_count   := 0;
        p_total_bonus := 0;
        DBMS_OUTPUT.PUT_LINE('[UpdateEmployeeBonus] UNEXPECTED ERROR: ' || SQLERRM);
END UpdateEmployeeBonus;
/

-- ============================================================
-- PROCEDURE 3: TransferFunds
-- ============================================================
-- Purpose  : Transfers a specified amount from one bank account
--            to another within the same bank, with full ACID
--            compliance, validation, and audit logging.
-- Params   : p_from_account_id  IN  - Source account ID
--            p_to_account_id    IN  - Destination account ID
--            p_amount           IN  - Amount to transfer
--            p_reference        OUT - System-generated reference no.
--            p_status           OUT - 'SUCCESS' or 'FAILED'
--            p_message          OUT - Human-readable status message
-- ============================================================
CREATE OR REPLACE PROCEDURE TransferFunds (
    p_from_account_id  IN   accounts.account_id%TYPE,
    p_to_account_id    IN   accounts.account_id%TYPE,
    p_amount           IN   NUMBER,
    p_reference        OUT  VARCHAR2,
    p_status           OUT  VARCHAR2,
    p_message          OUT  VARCHAR2
)
AS
    -- Source account details
    v_from_balance    accounts.balance%TYPE;
    v_from_status     accounts.status%TYPE;
    v_from_acc_no     accounts.account_number%TYPE;
    v_from_holder     accounts.account_holder%TYPE;

    -- Destination account details
    v_to_balance      accounts.balance%TYPE;
    v_to_status       accounts.status%TYPE;
    v_to_acc_no       accounts.account_number%TYPE;
    v_to_holder       accounts.account_holder%TYPE;

    -- Custom exceptions
    e_same_account       EXCEPTION;
    e_invalid_amount     EXCEPTION;
    e_source_not_found   EXCEPTION;
    e_dest_not_found     EXCEPTION;
    e_source_frozen      EXCEPTION;
    e_dest_frozen        EXCEPTION;
    e_insufficient_funds EXCEPTION;

    -- Minimum balance that must remain after transfer (regulatory requirement)
    c_min_balance  CONSTANT NUMBER := 1000;

BEGIN
    -- -------------------------------------------------------
    -- Step 1: Basic Input Validations
    -- -------------------------------------------------------
    IF p_from_account_id = p_to_account_id THEN
        RAISE e_same_account;
    END IF;

    IF p_amount <= 0 THEN
        RAISE e_invalid_amount;
    END IF;

    -- -------------------------------------------------------
    -- Step 2: Lock and fetch SOURCE account (FOR UPDATE prevents
    --         concurrent modification during the transaction)
    -- -------------------------------------------------------
    BEGIN
        SELECT balance, status, account_number, account_holder
        INTO   v_from_balance, v_from_status, v_from_acc_no, v_from_holder
        FROM   accounts
        WHERE  account_id = p_from_account_id
        FOR UPDATE;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN RAISE e_source_not_found;
    END;

    -- -------------------------------------------------------
    -- Step 3: Lock and fetch DESTINATION account
    -- -------------------------------------------------------
    BEGIN
        SELECT balance, status, account_number, account_holder
        INTO   v_to_balance, v_to_status, v_to_acc_no, v_to_holder
        FROM   accounts
        WHERE  account_id = p_to_account_id
        FOR UPDATE;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN RAISE e_dest_not_found;
    END;

    -- -------------------------------------------------------
    -- Step 4: Business Rule Validations
    -- -------------------------------------------------------
    IF v_from_status != 'ACTIVE' THEN
        RAISE e_source_frozen;
    END IF;

    IF v_to_status != 'ACTIVE' THEN
        RAISE e_dest_frozen;
    END IF;

    -- Check if source account has enough funds (keeping min balance)
    IF (v_from_balance - p_amount) < c_min_balance THEN
        RAISE e_insufficient_funds;
    END IF;

    -- -------------------------------------------------------
    -- Step 5: Generate a unique transfer reference number
    -- -------------------------------------------------------
    p_reference := 'TXN' || TO_CHAR(SYSDATE, 'YYYYMMDD') || '-' ||
                   LPAD(seq_log_id.NEXTVAL, 8, '0');

    -- -------------------------------------------------------
    -- Step 6: Debit source account
    -- -------------------------------------------------------
    UPDATE accounts
    SET    balance      = balance - p_amount,
           last_updated = SYSDATE
    WHERE  account_id   = p_from_account_id;

    -- Log DEBIT entry
    INSERT INTO transaction_log (
        log_id, account_id, transaction_type, amount,
        old_balance, new_balance, reference_id, remarks
    ) VALUES (
        seq_log_id.NEXTVAL,
        p_from_account_id,
        'TRANSFER_DEBIT',
        p_amount,
        v_from_balance,
        v_from_balance - p_amount,
        p_reference,
        'Funds transferred to account ' || v_to_acc_no || ' (' || v_to_holder || ')'
    );

    -- -------------------------------------------------------
    -- Step 7: Credit destination account
    -- -------------------------------------------------------
    UPDATE accounts
    SET    balance      = balance + p_amount,
           last_updated = SYSDATE
    WHERE  account_id   = p_to_account_id;

    -- Log CREDIT entry
    INSERT INTO transaction_log (
        log_id, account_id, transaction_type, amount,
        old_balance, new_balance, reference_id, remarks
    ) VALUES (
        seq_log_id.NEXTVAL,
        p_to_account_id,
        'TRANSFER_CREDIT',
        p_amount,
        v_to_balance,
        v_to_balance + p_amount,
        p_reference,
        'Funds received from account ' || v_from_acc_no || ' (' || v_from_holder || ')'
    );

    -- -------------------------------------------------------
    -- Step 8: Commit the entire transaction atomically
    -- -------------------------------------------------------
    COMMIT;

    p_status  := 'SUCCESS';
    p_message := 'INR ' || TO_CHAR(p_amount, 'FM99,99,99,999.00') ||
                 ' transferred from ' || v_from_acc_no || ' to ' || v_to_acc_no ||
                 '. Ref: ' || p_reference;

    DBMS_OUTPUT.PUT_LINE('[TransferFunds] ' || p_message);

-- -------------------------------------------------------
-- Exception Handlers
-- -------------------------------------------------------
EXCEPTION
    WHEN e_same_account THEN
        ROLLBACK;
        p_status    := 'FAILED';
        p_reference := NULL;
        p_message   := 'Source and destination accounts cannot be the same.';
        DBMS_OUTPUT.PUT_LINE('[TransferFunds] ERROR: ' || p_message);

    WHEN e_invalid_amount THEN
        ROLLBACK;
        p_status    := 'FAILED';
        p_reference := NULL;
        p_message   := 'Transfer amount must be greater than zero. Provided: ' || p_amount;
        DBMS_OUTPUT.PUT_LINE('[TransferFunds] ERROR: ' || p_message);

    WHEN e_source_not_found THEN
        ROLLBACK;
        p_status    := 'FAILED';
        p_reference := NULL;
        p_message   := 'Source account ID ' || p_from_account_id || ' not found.';
        DBMS_OUTPUT.PUT_LINE('[TransferFunds] ERROR: ' || p_message);

    WHEN e_dest_not_found THEN
        ROLLBACK;
        p_status    := 'FAILED';
        p_reference := NULL;
        p_message   := 'Destination account ID ' || p_to_account_id || ' not found.';
        DBMS_OUTPUT.PUT_LINE('[TransferFunds] ERROR: ' || p_message);

    WHEN e_source_frozen THEN
        ROLLBACK;
        p_status    := 'FAILED';
        p_reference := NULL;
        p_message   := 'Source account ' || v_from_acc_no || ' is ' || v_from_status ||
                       '. Transfer not allowed.';
        DBMS_OUTPUT.PUT_LINE('[TransferFunds] ERROR: ' || p_message);

    WHEN e_dest_frozen THEN
        ROLLBACK;
        p_status    := 'FAILED';
        p_reference := NULL;
        p_message   := 'Destination account ' || v_to_acc_no || ' is ' || v_to_status ||
                       '. Transfer not allowed.';
        DBMS_OUTPUT.PUT_LINE('[TransferFunds] ERROR: ' || p_message);

    WHEN e_insufficient_funds THEN
        ROLLBACK;
        p_status    := 'FAILED';
        p_reference := NULL;
        p_message   := 'Insufficient funds. Available: INR ' ||
                       TO_CHAR(v_from_balance - c_min_balance, 'FM99,99,99,999.00') ||
                       ' (after maintaining minimum balance of INR ' || c_min_balance || ').';
        DBMS_OUTPUT.PUT_LINE('[TransferFunds] ERROR: ' || p_message);

    WHEN OTHERS THEN
        ROLLBACK;
        p_status    := 'FAILED';
        p_reference := NULL;
        p_message   := 'Unexpected error: ' || SQLERRM;
        DBMS_OUTPUT.PUT_LINE('[TransferFunds] UNEXPECTED ERROR: ' || SQLERRM);
END TransferFunds;
/

-- ============================================================
-- SECTION 4: ANONYMOUS BLOCKS TO DEMONSTRATE EACH PROCEDURE
-- ============================================================

-- ------------------------------------------------------------
-- Demo 1A: ProcessMonthlyInterest - SUCCESSFUL case
--          Process interest on Aarav Sharma's SAVINGS account
-- ------------------------------------------------------------
DECLARE
    v_interest  NUMBER;
    v_balance   NUMBER;
    v_status    VARCHAR2(20);
BEGIN
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('============================================================');
    DBMS_OUTPUT.PUT_LINE('  DEMO 1A: ProcessMonthlyInterest - Savings Account (2001) ');
    DBMS_OUTPUT.PUT_LINE('============================================================');

    -- Call the procedure: use account's own stored rate (pass 0)
    ProcessMonthlyInterest(
        p_account_id   => 2001,
        p_annual_rate  => 0,        -- use account's stored 4% rate
        p_interest_amt => v_interest,
        p_new_balance  => v_balance,
        p_status       => v_status
    );

    DBMS_OUTPUT.PUT_LINE('Return Status   : ' || v_status);
    DBMS_OUTPUT.PUT_LINE('Interest Earned : INR ' || TO_CHAR(v_interest, 'FM99,99,999.00'));
    DBMS_OUTPUT.PUT_LINE('Updated Balance : INR ' || TO_CHAR(v_balance,  'FM99,99,999.00'));
END;
/

-- ------------------------------------------------------------
-- Demo 1B: ProcessMonthlyInterest - FAILED case (FROZEN account)
--          Attempt to process interest on a frozen account
-- ------------------------------------------------------------
DECLARE
    v_interest  NUMBER;
    v_balance   NUMBER;
    v_status    VARCHAR2(20);
BEGIN
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('============================================================');
    DBMS_OUTPUT.PUT_LINE('  DEMO 1B: ProcessMonthlyInterest - Frozen Account (2006)  ');
    DBMS_OUTPUT.PUT_LINE('============================================================');

    ProcessMonthlyInterest(
        p_account_id   => 2006,     -- Anjali Verma - FROZEN account
        p_annual_rate  => 5.5,
        p_interest_amt => v_interest,
        p_new_balance  => v_balance,
        p_status       => v_status
    );

    DBMS_OUTPUT.PUT_LINE('Return Status : ' || v_status);
END;
/

-- ------------------------------------------------------------
-- Demo 2A: UpdateEmployeeBonus - Successful - IT Department
-- ------------------------------------------------------------
DECLARE
    v_count       NUMBER;
    v_total_bonus NUMBER;
    v_status      VARCHAR2(20);
BEGIN
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('============================================================');
    DBMS_OUTPUT.PUT_LINE('  DEMO 2A: UpdateEmployeeBonus - IT Department (Dept 10)   ');
    DBMS_OUTPUT.PUT_LINE('============================================================');

    -- Apply a 15% bonus to all IT department employees
    UpdateEmployeeBonus(
        p_department_id => 10,
        p_bonus_pct     => 15,
        p_emp_count     => v_count,
        p_total_bonus   => v_total_bonus,
        p_status        => v_status
    );

    DBMS_OUTPUT.PUT_LINE('Status         : ' || v_status);
    DBMS_OUTPUT.PUT_LINE('Employees Updated : ' || v_count);
    DBMS_OUTPUT.PUT_LINE('Total Bonus Paid  : INR ' || TO_CHAR(v_total_bonus, 'FM99,99,999.00'));
END;
/

-- ------------------------------------------------------------
-- Demo 2B: UpdateEmployeeBonus - FAILED - Invalid bonus %
-- ------------------------------------------------------------
DECLARE
    v_count       NUMBER;
    v_total_bonus NUMBER;
    v_status      VARCHAR2(20);
BEGIN
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('============================================================');
    DBMS_OUTPUT.PUT_LINE('  DEMO 2B: UpdateEmployeeBonus - Invalid Bonus% (75%)      ');
    DBMS_OUTPUT.PUT_LINE('============================================================');

    UpdateEmployeeBonus(
        p_department_id => 30,
        p_bonus_pct     => 75,      -- Invalid: exceeds 50% cap
        p_emp_count     => v_count,
        p_total_bonus   => v_total_bonus,
        p_status        => v_status
    );

    DBMS_OUTPUT.PUT_LINE('Status : ' || v_status);
END;
/

-- ------------------------------------------------------------
-- Demo 3A: TransferFunds - SUCCESSFUL transfer
--          Transfer INR 50,000 from Aarav (2001) to Priya (2002)
-- ------------------------------------------------------------
DECLARE
    v_reference VARCHAR2(50);
    v_status    VARCHAR2(20);
    v_message   VARCHAR2(300);
BEGIN
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('============================================================');
    DBMS_OUTPUT.PUT_LINE('  DEMO 3A: TransferFunds - Valid Transfer 2001 -> 2002      ');
    DBMS_OUTPUT.PUT_LINE('============================================================');

    TransferFunds(
        p_from_account_id => 2001,   -- Aarav Sharma  (SB-100201)
        p_to_account_id   => 2002,   -- Priya Mehta   (SB-100202)
        p_amount          => 50000,
        p_reference       => v_reference,
        p_status          => v_status,
        p_message         => v_message
    );

    DBMS_OUTPUT.PUT_LINE('Status    : ' || v_status);
    DBMS_OUTPUT.PUT_LINE('Reference : ' || NVL(v_reference, 'N/A'));
    DBMS_OUTPUT.PUT_LINE('Message   : ' || v_message);
END;
/

-- ------------------------------------------------------------
-- Demo 3B: TransferFunds - FAILED - Insufficient Funds
--          Attempt to transfer INR 2,50,000 from Vikram (2005)
--          who only has INR 1,25,000
-- ------------------------------------------------------------
DECLARE
    v_reference VARCHAR2(50);
    v_status    VARCHAR2(20);
    v_message   VARCHAR2(300);
BEGIN
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('============================================================');
    DBMS_OUTPUT.PUT_LINE('  DEMO 3B: TransferFunds - Insufficient Funds (2005)        ');
    DBMS_OUTPUT.PUT_LINE('============================================================');

    TransferFunds(
        p_from_account_id => 2005,   -- Vikram Nair (INR 1,25,000 balance)
        p_to_account_id   => 2001,
        p_amount          => 250000, -- Exceeds available funds
        p_reference       => v_reference,
        p_status          => v_status,
        p_message         => v_message
    );

    DBMS_OUTPUT.PUT_LINE('Status  : ' || v_status);
    DBMS_OUTPUT.PUT_LINE('Message : ' || v_message);
END;
/

-- ------------------------------------------------------------
-- Demo 3C: TransferFunds - FAILED - Frozen destination account
-- ------------------------------------------------------------
DECLARE
    v_reference VARCHAR2(50);
    v_status    VARCHAR2(20);
    v_message   VARCHAR2(300);
BEGIN
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('============================================================');
    DBMS_OUTPUT.PUT_LINE('  DEMO 3C: TransferFunds - Frozen Destination (2006)        ');
    DBMS_OUTPUT.PUT_LINE('============================================================');

    TransferFunds(
        p_from_account_id => 2001,
        p_to_account_id   => 2006,   -- Anjali Verma - FROZEN account
        p_amount          => 10000,
        p_reference       => v_reference,
        p_status          => v_status,
        p_message         => v_message
    );

    DBMS_OUTPUT.PUT_LINE('Status  : ' || v_status);
    DBMS_OUTPUT.PUT_LINE('Message : ' || v_message);
END;
/

-- ============================================================
-- VERIFICATION QUERIES
-- Run these to verify results after demos
-- ============================================================

-- Check account balances after interest and transfers
SELECT account_id,
       account_number,
       account_holder,
       account_type,
       balance,
       last_updated
FROM   accounts
ORDER  BY account_id;

-- Check employee salaries after bonus update
SELECT e.employee_id,
       e.first_name || ' ' || e.last_name AS employee_name,
       d.dept_name,
       e.designation,
       e.salary
FROM   employees e
JOIN   departments d ON d.department_id = e.department_id
ORDER  BY e.department_id, e.employee_id;

-- Check full transaction audit log
SELECT log_id,
       account_id,
       transaction_type,
       TO_CHAR(amount,      'FM99,99,99,999.00') AS amount,
       TO_CHAR(old_balance, 'FM99,99,99,999.00') AS old_balance,
       TO_CHAR(new_balance, 'FM99,99,99,999.00') AS new_balance,
       reference_id,
       TO_CHAR(transaction_date, 'DD-MON-YYYY HH24:MI:SS') AS txn_date
FROM   transaction_log
ORDER  BY log_id;

-- ============================================================
-- END OF EXERCISE 3 : STORED PROCEDURES
-- ============================================================
