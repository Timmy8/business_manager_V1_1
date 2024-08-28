CREATE SCHEMA IF NOT EXISTS manager;

CREATE TABLE IF NOT EXISTS manager.client
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL CHECK (LENGTH(TRIM(name)) > 1),
    surname VARCHAR(50) NOT NULL CHECK (LENGTH(TRIM(surname)) > 1),
    phone_number VARCHAR(15) UNIQUE NOT NULL CHECK (LENGTH(TRIM(phone_number)) = 13),
    description VARCHAR(1000),
    blocked BOOLEAN
);

CREATE TABLE IF NOT EXISTS manager.proposal
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL CHECK (LENGTH(TRIM(name)) > 1),
    description VARCHAR(1000),
    price DECIMAL(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS manager.appointment
(
    id SERIAL PRIMARY KEY,
    visit_date TIMESTAMP NOT NULL,
    client_id int REFERENCES manager.client(id)
);

CREATE TABLE IF NOT EXISTS manager.appointment_proposals
(
    PRIMARY KEY (appointment_id, proposal_id),
    appointment_id INT REFERENCES manager.appointment(id),
    proposal_id INT REFERENCES manager.proposal(id)
);