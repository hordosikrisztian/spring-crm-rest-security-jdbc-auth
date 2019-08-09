CREATE DATABASE spring_security_demo_bcrypt;

CREATE SCHEMA IF NOT EXISTS ssd AUTHORIZATION springstudent;

DROP TABLE IF EXISTS
    ssd.users,
    ssd.authorities
;

CREATE TABLE ssd.users (
    username varchar(50) PRIMARY KEY,
    password char(68) NOT NULL,
    enabled boolean NOT NULL
);

-- Decrypted password: fun123
INSERT INTO ssd.users VALUES
    ('john', '{bcrypt}$2a$04$0HDg80TGpb5w3SfQwezfdedrdY.xmZUfrpwqjscFGmTUu8EeQfsHa', true),
    ('mary', '{bcrypt}$2a$04$0HDg80TGpb5w3SfQwezfdedrdY.xmZUfrpwqjscFGmTUu8EeQfsHa', true),
    ('susan', '{bcrypt}$2a$04$0HDg80TGpb5w3SfQwezfdedrdY.xmZUfrpwqjscFGmTUu8EeQfsHa', true)
;

CREATE TABLE ssd.authorities (
    username varchar(50) NOT NULL REFERENCES ssd.users (username),
    authority varchar(50) NOT NULL,
    UNIQUE (username, authority)
);

INSERT INTO ssd.authorities VALUES
    ('john', 'ROLE_EMPLOYEE'),
    ('mary', 'ROLE_EMPLOYEE'),
    ('mary', 'ROLE_MANAGER'),
    ('susan', 'ROLE_EMPLOYEE'),
    ('susan', 'ROLE_ADMIN')
;
