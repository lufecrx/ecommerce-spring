-- This script creates the tables for the cart module
-- The tables are shopping_cart and cart_item

-- The shopping_cart table has a foreign key to the user table
CREATE TABLE shopping_cart (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- The cart_item table has foreign keys to the shopping_cart and product tables
CREATE TABLE cart_item (
    id SERIAL PRIMARY KEY,
    shopping_cart_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (shopping_cart_id) REFERENCES shopping_cart(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Update the user table to add a shopping_cart_id column
ALTER TABLE users ADD COLUMN shopping_cart_id INT;

-- Indexes for the shopping_cart table to improve query performance
CREATE INDEX IF NOT EXISTS idx_shopping_cart_user_id ON shopping_cart(user_id);

-- Indexes for the cart_item table to improve query performance
CREATE INDEX IF NOT EXISTS idx_cart_item_cart_id ON cart_item(shopping_cart_id);

-- Indexes for the cart_item table to improve query performance
CREATE INDEX IF NOT EXISTS idx_cart_item_product_id ON cart_item(product_id);


