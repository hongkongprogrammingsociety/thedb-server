-- Insert users
INSERT INTO users (username, email, age) VALUES ('alice', 'alice@example.com', 25);
INSERT INTO users (username, email, age) VALUES ('bob', 'bob@example.com', 30);
INSERT INTO users (username, email, age) VALUES ('charlie', 'charlie@example.com', 35);
INSERT INTO users (username, email, age) VALUES ('diana', 'diana@example.com', 28);

-- Insert products
INSERT INTO products (name, description, price, stock) VALUES 
    ('Laptop', 'High-performance laptop', 1299.99, 10),
    ('Mouse', 'Wireless mouse', 29.99, 50),
    ('Keyboard', 'Mechanical keyboard', 89.99, 30),
    ('Monitor', '27-inch 4K monitor', 449.99, 15);

-- Insert orders
INSERT INTO orders (user_id, product_name, quantity, price) VALUES 
    (1, 'Laptop', 1, 1299.99),
    (1, 'Mouse', 2, 29.99),
    (2, 'Keyboard', 1, 89.99),
    (3, 'Monitor', 1, 449.99),
    (4, 'Mouse', 3, 29.99);
