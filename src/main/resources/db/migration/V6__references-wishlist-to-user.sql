ALTER TABLE wishlists 
ADD CONSTRAINT fk_wishlists_users 
FOREIGN KEY (user_id) REFERENCES users(id);