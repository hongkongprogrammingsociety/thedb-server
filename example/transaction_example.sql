-- Begin a transaction
BEGIN TRANSACTION;

-- Insert a new user
INSERT INTO users (username, email, age) VALUES ('eve', 'eve@example.com', 32);

-- Create an order for this user
INSERT INTO orders (user_id, product_name, quantity, price) 
VALUES (5, 'Keyboard', 1, 89.99);

-- Commit the transaction
COMMIT;

-- Rollback example
BEGIN TRANSACTION;

-- Delete all users (dangerous operation)
DELETE FROM users WHERE age > 100;

-- Oops, let's rollback
ROLLBACK;

-- Transaction with error handling
BEGIN TRANSACTION;

-- Update user age
UPDATE users SET age = 26 WHERE username = 'alice';

-- Update order price
UPDATE orders SET price = 1199.99 WHERE order_id = 1;

-- If everything is OK, commit
COMMIT;
