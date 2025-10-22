-- INNER JOIN - Get user orders
SELECT u.username, u.email, o.product_name, o.quantity, o.price
FROM users u
INNER JOIN orders o ON u.id = o.user_id;

-- LEFT JOIN - Get all users and their orders (including users with no orders)
SELECT u.username, o.order_id, o.product_name
FROM users u
LEFT JOIN orders o ON u.id = o.user_id;

-- Join with WHERE clause
SELECT u.username, o.product_name, o.price
FROM users u
INNER JOIN orders o ON u.id = o.user_id
WHERE o.price > 100;

-- Join with aggregation
SELECT u.username, COUNT(o.order_id) as total_orders, SUM(o.price * o.quantity) as total_spent
FROM users u
LEFT JOIN orders o ON u.id = o.user_id
GROUP BY u.id, u.username
ORDER BY total_spent DESC;

-- Multiple joins
SELECT u.username, o.order_id, p.name as product_name, p.price
FROM users u
INNER JOIN orders o ON u.id = o.user_id
INNER JOIN products p ON o.product_name = p.name;
