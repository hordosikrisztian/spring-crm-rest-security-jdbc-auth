DROP TABLE IF EXISTS wct.customer;

CREATE TABLE wct.customer (
    id serial PRIMARY KEY,
    first_name varchar(45) DEFAULT NULL,
    last_name varchar(45) DEFAULT NULL,
    email varchar(45) DEFAULT NULL
);

INSERT INTO wct.customer (first_name, last_name, email) VALUES
    ('David', 'Adams', 'david@luv2code.com'),
    ('John', 'Doe', 'john@luv2code.com'),
    ('Ajay', 'Rao', 'ajay@luv2code.com'),
    ('Mary', 'Public', 'mary@luv2code.com'),
    ('Maxwell', 'Dixon', 'max@luv2code.com')
;