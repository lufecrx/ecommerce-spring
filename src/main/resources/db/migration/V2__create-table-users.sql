-- This file contains the SQL script to create the table users in the database
-- The table is created after the tables in the V1__create-tables.sql file

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY NOT NULL UNIQUE,  
    login TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    role TEXT NOT NULL,

    email TEXT NOT NULL,
    birth_date DATE NOT NULL,
    mobile_phone TEXT NOT NULL
);
