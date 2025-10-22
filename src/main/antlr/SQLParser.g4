parser grammar SQLParser;

options {
    tokenVocab=SQLLexer;
}

// Root rule
sqlStatements
    : (sqlStatement SEMICOLON?)* EOF
    ;

sqlStatement
    : ddlStatement
    | dmlStatement
    | dqlStatement
    | transactionStatement
    | utilityStatement
    ;

// DDL Statements
ddlStatement
    : createTableStatement
    | dropTableStatement
    | alterTableStatement
    | createIndexStatement
    | dropIndexStatement
    | createDatabaseStatement
    | dropDatabaseStatement
    ;

createDatabaseStatement
    : CREATE DATABASE (IF NOT EXISTS)? databaseName
    ;

dropDatabaseStatement
    : DROP DATABASE (IF EXISTS)? databaseName
    ;

createTableStatement
    : CREATE TABLE (IF NOT EXISTS)? tableName LPAREN
        columnDefinition (COMMA columnDefinition)*
        (COMMA tableConstraint)*
      RPAREN
    ;

columnDefinition
    : columnName dataType columnConstraint*
    ;

dataType
    : INT | INTEGER | BIGINT | SMALLINT | TINYINT
    | DECIMAL (LPAREN INTEGER_LITERAL (COMMA INTEGER_LITERAL)? RPAREN)?
    | NUMERIC (LPAREN INTEGER_LITERAL (COMMA INTEGER_LITERAL)? RPAREN)?
    | FLOAT | DOUBLE | REAL
    | VARCHAR LPAREN INTEGER_LITERAL RPAREN
    | CHAR (LPAREN INTEGER_LITERAL RPAREN)?
    | TEXT | BLOB
    | DATE | TIME | DATETIME | TIMESTAMP
    | BOOLEAN | BOOL
    ;

columnConstraint
    : NOT NULL
    | NULL
    | PRIMARY KEY
    | UNIQUE
    | AUTO_INCREMENT
    | DEFAULT (literal | NOW LPAREN RPAREN | CURRENT_TIMESTAMP)
    | REFERENCES tableName LPAREN columnName RPAREN
    ;

tableConstraint
    : PRIMARY KEY LPAREN columnName (COMMA columnName)* RPAREN
    | UNIQUE (INDEX? indexName)? LPAREN columnName (COMMA columnName)* RPAREN
    | FOREIGN KEY LPAREN columnName (COMMA columnName)* RPAREN
      REFERENCES tableName LPAREN columnName (COMMA columnName)* RPAREN
    | CONSTRAINT constraintName CHECK LPAREN expression RPAREN
    ;

dropTableStatement
    : DROP TABLE (IF EXISTS)? tableName (COMMA tableName)*
    ;

alterTableStatement
    : ALTER TABLE tableName alterTableAction (COMMA alterTableAction)*
    ;

alterTableAction
    : ADD COLUMN? columnDefinition (FIRST | AFTER columnName)?
    | DROP COLUMN? columnName
    | MODIFY COLUMN? columnDefinition
    | CHANGE COLUMN? oldColName=columnName newColName=columnName dataType columnConstraint*
    | ADD tableConstraint
    | DROP PRIMARY KEY
    | DROP FOREIGN KEY constraintName
    | RENAME TO tableName
    ;

createIndexStatement
    : CREATE (UNIQUE)? INDEX indexName ON tableName LPAREN columnName (COMMA columnName)* RPAREN
    ;

dropIndexStatement
    : DROP INDEX indexName ON tableName
    ;

// DML Statements
dmlStatement
    : insertStatement
    | updateStatement
    | deleteStatement
    ;

insertStatement
    : INSERT INTO tableName
      (LPAREN columnName (COMMA columnName)* RPAREN)?
      VALUES valuesList (COMMA valuesList)*
    ;

valuesList
    : LPAREN expression (COMMA expression)* RPAREN
    ;

updateStatement
    : UPDATE tableName
      SET columnName EQUAL expression (COMMA columnName EQUAL expression)*
      (WHERE expression)?
    ;

deleteStatement
    : DELETE FROM tableName (WHERE expression)?
    ;

// DQL Statements
dqlStatement
    : selectStatement
    ;

selectStatement
    : SELECT (DISTINCT)? selectElements
      FROM tableSource (joinClause)*
      (WHERE whereExpression=expression)?
      (GROUP BY groupByItem (COMMA groupByItem)*)?
      (HAVING havingExpression=expression)?
      (ORDER BY orderByItem (COMMA orderByItem)*)?
      (LIMIT limitCount=INTEGER_LITERAL (OFFSET offsetCount=INTEGER_LITERAL)?)?
    ;

selectElements
    : MULTIPLY
    | selectElement (COMMA selectElement)*
    ;

selectElement
    : expression (AS? columnAlias)?
    ;

tableSource
    : tableName (AS? tableAlias)?
    | LPAREN selectStatement RPAREN AS? tableAlias
    ;

joinClause
    : joinType? JOIN tableSource (ON expression | USING LPAREN columnName (COMMA columnName)* RPAREN)
    ;

joinType
    : INNER
    | LEFT OUTER?
    | RIGHT OUTER?
    | FULL OUTER?
    | CROSS
    | NATURAL
    ;

groupByItem
    : expression
    ;

orderByItem
    : expression (ASC | DESC)?
    ;

// Transaction Statements
transactionStatement
    : BEGIN TRANSACTION?
    | START TRANSACTION
    | COMMIT
    | ROLLBACK
    | SAVEPOINT savepointName
    | ROLLBACK TO SAVEPOINT? savepointName
    ;

// Utility Statements
utilityStatement
    : SHOW TABLES
    | SHOW DATABASES
    | SHOW COLUMNS FROM tableName
    | DESCRIBE tableName
    | EXPLAIN selectStatement
    | USE databaseName
    ;

// Expressions
expression
    : literal                                                           # LiteralExpression
    | columnReference                                                   # ColumnExpression
    | functionCall                                                      # FunctionCallExpression
    | LPAREN expression RPAREN                                          # ParenExpression
    | expression MULTIPLY expression                                    # MultiplyExpression
    | expression DIVIDE expression                                      # DivideExpression
    | expression MODULO expression                                      # ModuloExpression
    | expression PLUS expression                                        # AddExpression
    | expression MINUS expression                                       # SubtractExpression
    | expression comparisonOperator expression                          # ComparisonExpression
    | expression IS NOT? NULL                                           # IsNullExpression
    | expression NOT? BETWEEN expression AND expression                 # BetweenExpression
    | expression NOT? IN LPAREN (expression (COMMA expression)* | selectStatement) RPAREN  # InExpression
    | expression NOT? LIKE expression                                   # LikeExpression
    | NOT expression                                                    # NotExpression
    | expression AND expression                                         # AndExpression
    | expression OR expression                                          # OrExpression
    | CASE (expression)? (WHEN expression THEN expression)+ (ELSE expression)? END  # CaseExpression
    | EXISTS LPAREN selectStatement RPAREN                              # ExistsExpression
    | LPAREN selectStatement RPAREN                                     # SubqueryExpression
    ;

comparisonOperator
    : EQUAL | NOT_EQUAL | LESS_THAN | LESS_EQUAL | GREATER_THAN | GREATER_EQUAL
    ;

columnReference
    : (tableName DOT)? columnName
    ;

functionCall
    : COUNT LPAREN (MULTIPLY | DISTINCT? expression) RPAREN
    | (SUM | AVG | MIN | MAX) LPAREN DISTINCT? expression RPAREN
    | CONCAT LPAREN expression (COMMA expression)* RPAREN
    | SUBSTRING LPAREN expression COMMA expression (COMMA expression)? RPAREN
    | UPPER LPAREN expression RPAREN
    | LOWER LPAREN expression RPAREN
    | TRIM LPAREN expression RPAREN
    | LENGTH LPAREN expression RPAREN
    | NOW LPAREN RPAREN
    | CURRENT_DATE LPAREN RPAREN
    | CURRENT_TIME LPAREN RPAREN
    | CURRENT_TIMESTAMP LPAREN RPAREN
    | functionName LPAREN (expression (COMMA expression)*)? RPAREN
    ;

literal
    : INTEGER_LITERAL
    | DECIMAL_LITERAL
    | STRING_LITERAL
    | DOUBLE_QUOTED_STRING
    | TRUE
    | FALSE
    | NULL
    ;

// Names
tableName : (databaseName DOT)? identifier;
columnName : identifier;
indexName : identifier;
constraintName : identifier;
databaseName : identifier;
savepointName : identifier;
functionName : identifier;
columnAlias : identifier;
tableAlias : identifier;

identifier
    : IDENTIFIER
    | QUOTED_IDENTIFIER
    // Allow keywords as identifiers when quoted
    | nonReservedKeyword
    ;

// Non-reserved keywords that can be used as identifiers
nonReservedKeyword
    : AFTER | CHANGE | FIRST | MODIFY
    | TEXT | BLOB | TIMESTAMP
    ;
