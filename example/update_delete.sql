-- Update user information
UPDATE users SET email = 'alice.new@example.com' WHERE username = 'alice';

-- Update multiple columns
UPDATE users SET age = 31, email = 'bob.updated@example.com' WHERE username = 'bob';

-- Update with calculation
UPDATE products SET price = price * 1.1 WHERE product_id < 3;

-- Delete a specific user
DELETE FROM users WHERE username = 'eve';

-- Delete users by condition
DELETE FROM users WHERE age < 18;

-- Alter table - add column
ALTER TABLE users ADD COLUMN phone VARCHAR(20);

-- Alter table - modify column
ALTER TABLE users MODIFY COLUMN email VARCHAR(150);

-- Alter table - drop column
ALTER TABLE users DROP COLUMN phone;

-- Drop table
DROP TABLE IF EXISTS old_table;
