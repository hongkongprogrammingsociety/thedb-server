# TheDB Usage Guide

## Command Line Interface

### Start Server
```bash
java -jar thedb-server.jar server [--port 3306] [--data-dir ./data]
```

### Execute SQL File
```bash
java -jar thedb-server.jar execute script.sql
```

### Interactive Console
```bash
java -jar thedb-server.jar console
```

### Parse and Validate SQL
```bash
java -jar thedb-server.jar validate script.sql
```

## SQL Syntax Examples

### CREATE TABLE
```sql
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL,
    age INT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

### INSERT
```sql
INSERT INTO users (username, email, age) VALUES ('alice', 'alice@example.com', 25);
INSERT INTO users (username, email, age) VALUES 
    ('bob', 'bob@example.com', 30),
    ('charlie', 'charlie@example.com', 35);
```

### SELECT
```sql
SELECT * FROM users;
SELECT username, email FROM users WHERE age > 25;
SELECT COUNT(*) FROM users;
SELECT age, COUNT(*) as count FROM users GROUP BY age;
```

### UPDATE
```sql
UPDATE users SET age = 26 WHERE username = 'alice';
```

### DELETE
```sql
DELETE FROM users WHERE age < 20;
```

### JOIN
```sql
SELECT u.username, o.order_id, o.total
FROM users u
INNER JOIN orders o ON u.id = o.user_id
WHERE o.total > 100;
```

### CREATE INDEX
```sql
CREATE INDEX idx_email ON users(email);
CREATE UNIQUE INDEX idx_username ON users(username);
```

### DROP TABLE
```sql
DROP TABLE IF EXISTS old_users;
```

### ALTER TABLE
```sql
ALTER TABLE users ADD COLUMN phone VARCHAR(20);
ALTER TABLE users DROP COLUMN age;
ALTER TABLE users MODIFY COLUMN email VARCHAR(150);
```

### TRANSACTIONS
```sql
BEGIN;
UPDATE accounts SET balance = balance - 100 WHERE id = 1;
UPDATE accounts SET balance = balance + 100 WHERE id = 2;
COMMIT;

-- Rollback example
BEGIN;
DELETE FROM users WHERE age > 80;
ROLLBACK;
```

## Data Types

- **INT**: 32-bit integer
- **BIGINT**: 64-bit integer
- **VARCHAR(n)**: Variable-length string up to n characters
- **TEXT**: Large text field
- **DECIMAL(p,s)**: Fixed-point decimal number
- **DATE**: Date (YYYY-MM-DD)
- **DATETIME**: Date and time (YYYY-MM-DD HH:MM:SS)
- **BOOLEAN**: True/false value

## Constraints

- **PRIMARY KEY**: Unique identifier for rows
- **NOT NULL**: Column cannot be null
- **UNIQUE**: All values must be unique
- **DEFAULT**: Default value when not specified
- **AUTO_INCREMENT**: Automatically increment integer values
- **FOREIGN KEY**: Reference to another table's primary key

## Functions

- **Aggregate**: COUNT, SUM, AVG, MIN, MAX
- **String**: CONCAT, SUBSTRING, UPPER, LOWER, TRIM
- **Date**: NOW, CURRENT_DATE, DATE_FORMAT
- **Math**: ABS, CEIL, FLOOR, ROUND, MOD
- **Conditional**: IF, CASE, COALESCE, NULLIF
