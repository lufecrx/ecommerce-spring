-- This script updates the users table to add the columns from the OneTimePassword record and the is_enabled column
-- The columns are otp and otp_generation_time

ALTER TABLE users add COLUMN is_enabled BOOLEAN DEFAULT false;
ALTER TABLE users ADD COLUMN otp TEXT;
ALTER TABLE users ADD COLUMN otp_generation_time TIMESTAMP;

-- This script update the wishlists table to add the columns from the user_id record
-- This is needed to link the wishlist to the user

ALTER TABLE wishlists add COLUMN user_id BIGINT NOT NULL;