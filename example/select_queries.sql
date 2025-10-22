-- Select all users
SELECT * FROM users;

-- Select specific columns
SELECT username, email FROM users;

-- Select with WHERE clause
SELECT * FROM users WHERE age > 25;

-- Select with ORDER BY
SELECT * FROM users ORDER BY age DESC;

-- Select with LIMIT
SELECT * FROM users LIMIT 2;

-- Count users
SELECT COUNT(*) as total_users FROM users;

-- Group by age
SELECT age, COUNT(*) as count FROM users GROUP BY age;

-- Select with aggregation
SELECT AVG(age) as average_age, MIN(age) as min_age, MAX(age) as max_age FROM users;

-- Select products by price range
SELECT * FROM products WHERE price BETWEEN 50 AND 500;

-- Select with LIKE
SELECT * FROM users WHERE username LIKE 'a%';

-- Select DISTINCT
SELECT DISTINCT age FROM users;
