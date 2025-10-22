-- Create an index on email column for faster lookups
CREATE INDEX idx_users_email ON users(email);

-- Create a unique index on username
CREATE UNIQUE INDEX idx_users_username ON users(username);

-- Create a composite index on orders
CREATE INDEX idx_orders_user_date ON orders(user_id, order_date);

-- Create an index on products price
CREATE INDEX idx_products_price ON products(price);

-- Query that benefits from index
SELECT * FROM users WHERE email = 'alice@example.com';

-- Drop an index
DROP INDEX idx_products_price ON products;
