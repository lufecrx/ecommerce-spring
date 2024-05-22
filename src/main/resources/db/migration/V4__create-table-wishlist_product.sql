-- This script creates the table wishlist_product with a composite primary key and foreign keys to the wishlists and products tables.
-- The table is used to store the many-to-many relationship between wishlists and products.

CREATE TABLE wishlist_product (
    wishlist_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    PRIMARY KEY (wishlist_id, product_id),
    FOREIGN KEY (wishlist_id) REFERENCES wishlists(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- This script drops the wishlist_id column from the products table.
-- The column is no longer needed as the many-to-many relationship between wishlists and products is now managed by the wishlist_product table.

ALTER TABLE products
DROP COLUMN wishlist_id;