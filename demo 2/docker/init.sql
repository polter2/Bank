BEGIN;

CREATE TABLE customer (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    sex VARCHAR(10) NOT NULL,
    nationality VARCHAR(100) NOT NULL,
    date_of_birth DATE NOT NULL,
    card_number VARCHAR(20) NOT NULL,
    card_date_of_issue DATE NOT NULL,
    card_date_of_expiry DATE NOT NULL
);

CREATE TABLE account (
    id SERIAL PRIMARY KEY,
    iban VARCHAR(34) UNIQUE NOT NULL,
    currency VARCHAR(3) NOT NULL,
    balance DECIMAL(15, 2) NOT NULL,
    customer_id INTEGER NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);

CREATE TABLE transaction_transfer (
    id SERIAL PRIMARY KEY,
    date_transaction TIMESTAMP NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    debtor_iban VARCHAR(34) NOT NULL,
    creditor_iban VARCHAR(34) NOT NULL,
    message TEXT,
    FOREIGN KEY (debtor_iban) REFERENCES account(iban),
    FOREIGN KEY (creditor_iban) REFERENCES account(iban)
);

COMMIT;