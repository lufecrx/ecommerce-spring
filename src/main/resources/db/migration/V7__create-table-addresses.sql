-- This is a SQL script for creating the addresses table
-- The table will store the addresses of the users

CREATE TABLE addresses (
    id BIGSERIAL PRIMARY KEY,
    street TEXT NOT NULL,
    city TEXT NOT NULL,
    state TEXT NOT NULL,
    postal_code TEXT NOT NULL CHECK (postal_code ~ '^\d{5}-\d{3}$'),
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- This script creates the index on the user_id column
CREATE INDEX idx_addresses_user_id ON addresses(user_id);

