-- This file contains the SQL script to create the tables in the database
-- The tables are created in the order of their dependencies

-- We need to create the categories table before creating the product_categories table
CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- We need to create the wishlists table before creating the products table
CREATE TABLE wishlists (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- We need to create the products table before creating the product_categories table
CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    wishlist_id BIGINT,
    FOREIGN KEY (wishlist_id) REFERENCES wishlists(id)
);

-- We need to create the product_categories table after creating the products and categories tables
CREATE TABLE product_categories (
    product_id BIGINT,
    category_id BIGINT,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);
