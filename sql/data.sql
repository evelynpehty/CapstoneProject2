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
INSERT INTO ACCOUNT (account_id, nric, account_balance, account_created_date, account_type)
VALUES (account_id_seq.NEXTVAL, 'A1234567Z', 1200.00, TO_DATE('2023-08-15', 'YYYY-MM-DD'), 'Savings');
INSERT INTO ACCOUNT (account_id, nric, account_balance, account_created_date, account_type)
VALUES (account_id_seq.NEXTVAL, 'B9876543Q', 4000.00, TO_DATE('2023-08-20', 'YYYY-MM-DD'), 'Savings');
INSERT INTO ACCOUNT (account_id, nric, account_balance, account_created_date, account_type)
VALUES (account_id_seq.NEXTVAL, 'A1234567Z', 6200.00, TO_DATE('2023-08-30', 'YYYY-MM-DD'), 'Checking');
INSERT INTO ACCOUNT (account_id, nric, account_balance, account_created_date, account_type)
VALUES (account_id_seq.NEXTVAL, 'B9876543Q', 2500.00, TO_DATE('2023-09-20', 'YYYY-MM-DD'), 'Fixed Deposit');

-- Insert dummy data into the TRANSACTION table
INSERT INTO TRANSACTION (transaction_id, account_id, transaction_type, transaction_value, transaction_datetime)
VALUES (transaction_id_seq.NEXTVAL, 1, 'Deposit', 2000.00, TO_DATE('2023-08-22', 'YYYY-MM-DD'));
INSERT INTO TRANSACTION (transaction_id, account_id, transaction_type, transaction_value, transaction_datetime)
VALUES (transaction_id_seq.NEXTVAL, 2, 'Deposit', 5000.00, TO_DATE('2023-08-25', 'YYYY-MM-DD'));
INSERT INTO TRANSACTION (transaction_id, account_id, transaction_type, transaction_value, transaction_datetime, transaction_party_accountid)
VALUES (transaction_id_seq.NEXTVAL, 1, 'Transfer', 500.00, TO_DATE('2023-08-29', 'YYYY-MM-DD'), 2);
INSERT INTO TRANSACTION (transaction_id, account_id, transaction_type, transaction_value, transaction_datetime)
VALUES (transaction_id_seq.NEXTVAL, 3, 'Deposit', 6000.00, TO_DATE('2023-09-01', 'YYYY-MM-DD'));
INSERT INTO TRANSACTION (transaction_id, account_id, transaction_type, transaction_value, transaction_datetime)
VALUES (transaction_id_seq.NEXTVAL, 1, 'Withdraw', 300.00, TO_DATE('2023-09-05', 'YYYY-MM-DD'));
INSERT INTO TRANSACTION (transaction_id, account_id, transaction_type, transaction_value, transaction_datetime)
VALUES (transaction_id_seq.NEXTVAL, 3, 'Withdraw', 800.00, TO_DATE('2023-09-15', 'YYYY-MM-DD'));
INSERT INTO TRANSACTION (transaction_id, account_id, transaction_type, transaction_value, transaction_datetime, transaction_party_accountid)
VALUES (transaction_id_seq.NEXTVAL, 2, 'Transfer', 1000.00, TO_DATE('2023-09-22', 'YYYY-MM-DD'), 3);
INSERT INTO TRANSACTION (transaction_id, account_id, transaction_type, transaction_value, transaction_datetime, transaction_party_accountid)
VALUES (transaction_id_seq.NEXTVAL, 2, 'Transfer', 500.00, TO_DATE('2023-09-23', 'YYYY-MM-DD'), 4);
INSERT INTO TRANSACTION (transaction_id, account_id, transaction_type, transaction_value, transaction_datetime)
VALUES (transaction_id_seq.NEXTVAL, 4, 'Deposit', 2000.00, TO_DATE('2023-09-25', 'YYYY-MM-DD'));
COMMIT;