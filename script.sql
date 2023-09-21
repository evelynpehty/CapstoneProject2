DECLARE
    table_count NUMBER;

BEGIN
    -- Drop tables if they exist
    SELECT COUNT(*) INTO table_count
    FROM user_tables
    WHERE table_name = 'ACCOUNT';
    IF table_count = 1 THEN
        EXECUTE IMMEDIATE 'DROP TABLE ACCOUNT cascade constraints';
    END IF;
    
    SELECT COUNT(*) INTO table_count
    FROM user_tables
    WHERE table_name = 'ACCOUNTTYPE';
    IF table_count = 1 THEN
        EXECUTE IMMEDIATE 'DROP TABLE ACCOUNTTYPE cascade constraints';
    END IF;
    
    SELECT COUNT(*) INTO table_count
    FROM user_tables
    WHERE table_name = 'CUSTOMER';
    IF table_count = 1 THEN
        EXECUTE IMMEDIATE 'DROP TABLE CUSTOMER cascade constraints';
    END IF;

    SELECT COUNT(*) INTO table_count
    FROM user_tables
    WHERE table_name = 'TELLER';
    IF table_count = 1 THEN
        EXECUTE IMMEDIATE 'DROP TABLE TELLER cascade constraints';
    END IF;
    
    SELECT COUNT(*) INTO table_count
    FROM user_tables
    WHERE table_name = 'TRANSACTION';
    IF table_count = 1 THEN
        EXECUTE IMMEDIATE 'DROP TABLE TRANSACTION cascade constraints';
    END IF;
    
    -- Create the CUSTOMER table
    EXECUTE IMMEDIATE '
    CREATE TABLE CUSTOMER (
        nric                VARCHAR2(10)      NOT NULL    PRIMARY KEY,
        first_name          VARCHAR2(200)     NOT NULL,
        last_name           VARCHAR2(200)     NOT NULL,
        gender              VARCHAR2(20)      NOT NULL,
        phone_number        VARCHAR2(20)      NOT NULL,
        dob                 DATE              NOT NULL,
        email               VARCHAR2(100),
        nationality         VARCHAR2(50)      NOT NULL
    )';
    
    -- Create the TELLER table
    EXECUTE IMMEDIATE '
    CREATE TABLE TELLER (
        teller_id         NUMBER          PRIMARY KEY,
        teller_username   VARCHAR2(100)   NOT NULL,
        teller_password   VARCHAR2(100)   NOT NULL
    )';
    
    -- Create the ACCOUNTTYPE table
    EXECUTE IMMEDIATE '
    CREATE TABLE ACCOUNTTYPE (
        account_type            VARCHAR2(50)    PRIMARY KEY,
        account_description     VARCHAR2(255)
    )';
    
    -- Create the ACCOUNT table
    EXECUTE IMMEDIATE '
    CREATE TABLE ACCOUNT (
        account_id              NUMBER            PRIMARY KEY,
        nric                    VARCHAR2(20)      NOT NULL,
        account_balance         NUMBER(10, 2)     DEFAULT 0       NOT NULL,
        account_created_date    DATE              DEFAULT SYSDATE NOT NULL,
        isAccountActive         NUMBER(1)         DEFAULT 1       NOT NULL,
        account_type            VARCHAR2(50),
        CONSTRAINT fk_nric FOREIGN KEY (nric) REFERENCES CUSTOMER(nric)
        on delete cascade,
        CONSTRAINT fk_accountType FOREIGN KEY (account_type) REFERENCES ACCOUNTTYPE(account_type)
        on delete cascade
    )';
    
    -- Create the TRANSACTION table
    EXECUTE IMMEDIATE '
    CREATE TABLE TRANSACTION (
        transaction_id                  NUMBER          PRIMARY KEY,
        account_id                      NUMBER          NOT NULL,
        transaction_type                VARCHAR2(20)    NOT NULL,
        transaction_value               NUMBER(12, 2)    NOT NULL,
        transaction_datetime            DATE            DEFAULT SYSDATE,
        transaction_party_accountid     NUMBER,
        CONSTRAINT fk_account_id FOREIGN KEY (account_id) REFERENCES ACCOUNT(account_id)
        on delete cascade,
        CONSTRAINT fk_transaction_party_accountid FOREIGN KEY (transaction_party_accountid) REFERENCES ACCOUNT(account_id)
        on delete cascade
    )';
    
    -- Create a sequence for the account ID
    EXECUTE IMMEDIATE '
    CREATE SEQUENCE account_id_seq
        START WITH 1
        INCREMENT BY 1
        NOCACHE
        NOCYCLE
    ';
    
    -- Create a sequence for the transaction ID
    EXECUTE IMMEDIATE '
    CREATE SEQUENCE transaction_id_seq
        START WITH 1
        INCREMENT BY 1
        NOCACHE
        NOCYCLE
    ';

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
