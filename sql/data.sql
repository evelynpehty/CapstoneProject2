-- Insert dummy data into the CUSTOMER table
INSERT INTO CUSTOMER (nric, first_name, last_name, gender, phone_number, dob, email, nationality) 
VALUES ('A1234567Z', 'John', 'Doe', 'Male', '11111111', TO_DATE('1980-01-01', 'YYYY-MM-DD'), 'john@example.com', 'SG');
INSERT INTO CUSTOMER (nric, first_name, last_name, gender, phone_number, dob, email, nationality)
VALUES ('B9876543Q', 'Jane', 'Smith', 'Female', '12365478578', TO_DATE('1990-03-15', 'YYYY-MM-DD'), 'jane@example.com', 'CHN');

-- Insert dummy data into the TELLER table
INSERT INTO TELLER (teller_id, teller_username, teller_password)
VALUES (1, 'teller1', 'password1');
INSERT INTO TELLER (teller_id, teller_username, teller_password)
VALUES (2, 'teller2', 'password2');

-- Insert dummy data into the ACCOUNTTYPE table
INSERT INTO ACCOUNTTYPE (account_type, account_description)
VALUES ('Savings', 'Savings Account');
INSERT INTO ACCOUNTTYPE (account_type, account_description)
VALUES ('Checking', 'Checking Account');
INSERT INTO ACCOUNTTYPE (account_type, account_description)
VALUES ('Fixed Deposit', 'Fixed Deposit Account');

-- Insert dummy data into the ACCOUNT table
INSERT INTO ACCOUNT (account_id, nric, account_balance, account_type)
VALUES (1, 'A1234567Z', 1000.00, 'Savings');
INSERT INTO ACCOUNT (account_id, nric, account_balance, account_type)
VALUES (2, 'A1234567Z', 400.00, 'Checking');
INSERT INTO ACCOUNT (account_id, nric, account_balance, account_type)
VALUES (3, 'B9876543Q', 7000.00, 'Savings');

-- Insert dummy data into the TRANSACTION table
INSERT INTO TRANSACTION (transaction_id, account_id, transaction_type, transaction_value)
VALUES (1, 1, 'Deposit', 500.00);
INSERT INTO TRANSACTION (transaction_id, account_id, transaction_type, transaction_value, transaction_party_accountid)
VALUES (2, 2, 'Transfer', 500.00, 3);
INSERT INTO TRANSACTION (transaction_id, account_id, transaction_type, transaction_value)
VALUES (3, 3, 'Withdraw', 500.00);