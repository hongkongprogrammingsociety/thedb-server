package org.hkprog.thedb.ast;

/**
 * Base interface for all AST nodes
 */
public interface ASTNode {
    /**
     * Accept a visitor to traverse this node
     */
    <T> T accept(ASTVisitor<T> visitor);
    
    /**
     * Get the type of this AST node
     */
    NodeType getNodeType();
    
    /**
     * Types of AST nodes
     */
    enum NodeType {
        // DDL
        CREATE_TABLE,
        DROP_TABLE,
        ALTER_TABLE,
        CREATE_INDEX,
        DROP_INDEX,
        CREATE_DATABASE,
        DROP_DATABASE,
        
        // DML
        INSERT,
        UPDATE,
        DELETE,
        
        // DQL
        SELECT,
        
        // Transaction
        BEGIN_TRANSACTION,
        COMMIT,
        ROLLBACK,
        
        // Expressions
        LITERAL,
        COLUMN_REFERENCE,
        BINARY_OPERATION,
        UNARY_OPERATION,
        FUNCTION_CALL,
        CASE_EXPRESSION,
        SUBQUERY,
        
        // Clauses
        WHERE_CLAUSE,
        JOIN_CLAUSE,
        GROUP_BY_CLAUSE,
        ORDER_BY_CLAUSE,
        LIMIT_CLAUSE,
        
        // Other
        COLUMN_DEFINITION,
        TABLE_CONSTRAINT,
        DATA_TYPE,
        PLACEHOLDER,
        STATEMENT_LIST
    }
}
