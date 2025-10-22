# TheDB Server - Project Structure Summary

This document summarizes the project structure created for thedb-server, a MySQL-like database written in Java with ANTLR.

## Project Overview

**thedb-server** is a relational database management system inspired by MySQL, built purely in Java using:
- **ANTLR 4.13.1** for SQL parsing
- **Maven** for build management
- **Java 17** as the minimum Java version

## Directory Structure

```
thedb-server/
├── pom.xml                      # Maven project configuration
├── nbactions.xml                # NetBeans actions
├── README.md                    # Main project documentation
├── USAGE.md                     # Usage guide and SQL examples
├── .gitignore                   # Git ignore patterns
│
├── example/                     # Example SQL scripts
│   ├── README.md
│   ├── create_tables.sql        # CREATE TABLE examples
│   ├── insert_data.sql          # INSERT examples
│   ├── select_queries.sql       # SELECT queries
│   ├── join_example.sql         # JOIN operations
│   ├── transaction_example.sql  # Transaction examples
│   ├── index_example.sql        # Index creation
│   └── update_delete.sql        # UPDATE/DELETE/ALTER
│
└── src/main/
    ├── antlr/                   # ANTLR grammar files
    │   ├── SQLLexer.g4          # SQL lexer grammar
    │   └── SQLParser.g4         # SQL parser grammar
    │
    └── java/org/hkprog/thedb/
        ├── TheDB.java           # Main entry point
        │
        ├── ast/                 # Abstract Syntax Tree nodes
        │   ├── ASTNode.java
        │   ├── ASTVisitor.java
        │   ├── LiteralNode.java
        │   ├── ColumnReferenceNode.java
        │   ├── BinaryOperationNode.java
        │   ├── UnaryOperationNode.java
        │   ├── FunctionCallNode.java
        │   ├── SelectStatementNode.java
        │   ├── InsertStatementNode.java
        │   ├── UpdateStatementNode.java
        │   ├── DeleteStatementNode.java
        │   ├── CreateTableStatementNode.java
        │   ├── ColumnDefinitionNode.java
        │   ├── TableConstraintNode.java
        │   └── OtherASTNodes.java
        │
        ├── compiler/            # SQL compilation
        │   ├── SQLCompiler.java
        │   └── SQLASTBuilder.java
        │
        ├── planner/             # Query planning
        │   └── QueryPlanner.java
        │
        ├── executor/            # Query execution
        │   └── QueryExecutor.java
        │
        ├── storage/             # Storage engine
        │   └── StorageEngine.java
        │
        ├── server/              # TCP/IP server
        │   └── TheDBServer.java
        │
        └── console/             # Interactive console
            └── InteractiveConsole.java
```

## Component Architecture

### 1. **ANTLR Grammar** (`src/main/antlr/`)
- **SQLLexer.g4**: Tokenizes SQL input (keywords, identifiers, literals, operators)
- **SQLParser.g4**: Parses tokens into parse trees (DDL, DML, DQL statements)

### 2. **AST Nodes** (`org.hkprog.thedb.ast`)
Type-safe representation of SQL statements:
- **Statement Nodes**: SELECT, INSERT, UPDATE, DELETE, CREATE TABLE, etc.
- **Expression Nodes**: Literals, column references, binary/unary operations, functions
- **Definition Nodes**: Column definitions, table constraints

### 3. **Compiler** (`org.hkprog.thedb.compiler`)
- **SQLCompiler**: Main compilation entry point
- **SQLASTBuilder**: Converts ANTLR parse trees to AST nodes

### 4. **Query Planner** (`org.hkprog.thedb.planner`)
- **QueryPlanner**: Creates optimized execution plans from AST
- Index selection and cost-based optimization

### 5. **Query Executor** (`org.hkprog.thedb.executor`)
- **QueryExecutor**: Executes query plans
- Implements joins, aggregations, filtering, sorting

### 6. **Storage Engine** (`org.hkprog.thedb.storage`)
- **StorageEngine**: Manages persistent data
- B+ Tree indexes, page-based storage, transaction support

### 7. **Server** (`org.hkprog.thedb.server`)
- **TheDBServer**: TCP/IP server for client connections
- Multi-threaded request handling

### 8. **Console** (`org.hkprog.thedb.console`)
- **InteractiveConsole**: Interactive SQL shell

## Supported SQL Features

### DDL (Data Definition Language)
- CREATE TABLE, DROP TABLE, ALTER TABLE
- CREATE INDEX, DROP INDEX
- CREATE DATABASE, DROP DATABASE

### DML (Data Manipulation Language)
- INSERT, UPDATE, DELETE

### DQL (Data Query Language)
- SELECT with WHERE, GROUP BY, HAVING, ORDER BY, LIMIT
- JOINs: INNER, LEFT, RIGHT, FULL OUTER, CROSS
- Subqueries and CASE expressions

### Transactions
- BEGIN TRANSACTION, COMMIT, ROLLBACK

### Data Types
- Numeric: INT, BIGINT, DECIMAL, FLOAT, DOUBLE
- String: VARCHAR, CHAR, TEXT
- Date/Time: DATE, DATETIME, TIMESTAMP
- Boolean: BOOLEAN

### Constraints
- PRIMARY KEY, FOREIGN KEY, UNIQUE
- NOT NULL, DEFAULT, AUTO_INCREMENT

## Build Instructions

```bash
# Build the project
mvn clean package

# This will:
# 1. Generate ANTLR lexer and parser from .g4 files
# 2. Compile Java sources
# 3. Create executable JAR with all dependencies
```

## Usage

```bash
# Start database server
java -jar target/thedb-server-0.0.1-shaded.jar server --port 3306

# Execute SQL script
java -jar target/thedb-server-0.0.1-shaded.jar execute example/create_tables.sql

# Interactive console
java -jar target/thedb-server-0.0.1-shaded.jar console

# Validate SQL syntax
java -jar target/thedb-server-0.0.1-shaded.jar validate queries.sql
```

## Comparison with egg-tart

| Aspect | egg-tart | thedb-server |
|--------|----------|--------------|
| **Purpose** | Web scripting language | Relational database |
| **Language Paradigm** | PHP-like scripting | SQL |
| **Runtime** | JVM bytecode generation | Query execution engine |
| **Storage** | N/A | Persistent B+ tree storage |
| **Main Use Case** | Web development | Data management |
| **Syntax** | `<?et ... ?>` tags | Standard SQL |
| **Execution** | Script interpretation | Query planning & execution |

## Key Similarities with egg-tart

1. **Build System**: Maven with same structure
2. **ANTLR Integration**: Both use ANTLR for parsing
3. **AST Pattern**: Both use AST visitor pattern
4. **Package Structure**: Similar org.hkprog.* organization
5. **Entry Point**: Main class with CLI commands
6. **Examples**: Both include example files

## Next Steps for Implementation

1. **Complete SQLASTBuilder**: Implement all visit methods after ANTLR generation
2. **Implement Storage Engine**: Add B+ tree indexes, page management, WAL
3. **Query Executor**: Implement join algorithms, aggregations, filters
4. **Transaction Manager**: ACID properties, isolation levels
5. **Network Protocol**: MySQL wire protocol compatibility
6. **Testing**: Unit tests for each component

## Notes

- The project structure mirrors egg-tart's organization
- ANTLR classes will be generated in `target/generated-sources/` after Maven build
- Storage files will be created in the `data/` directory
- The current implementation includes stubs - full functionality requires completing the storage engine and executor

## Author

Peter <peter@hkprog.org>
Hong Kong Programming Society
