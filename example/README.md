# TheDB Examples

This directory contains example SQL scripts demonstrating TheDB features.

## Examples

- `create_tables.sql` - Create sample database tables
- `insert_data.sql` - Insert sample data
- `select_queries.sql` - Various SELECT queries
- `join_example.sql` - JOIN operations
- `transaction_example.sql` - Transaction operations
- `index_example.sql` - Index creation

## Running Examples

```bash
# Execute a SQL script
java -jar target/thedb-server-0.0.1-shaded.jar execute example/create_tables.sql

# Or use interactive console
java -jar target/thedb-server-0.0.1-shaded.jar console
```
