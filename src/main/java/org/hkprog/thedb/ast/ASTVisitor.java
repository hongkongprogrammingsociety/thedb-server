package org.hkprog.thedb.ast;

/**
 * Visitor interface for traversing AST nodes
 */
public interface ASTVisitor<T> {
    
    // DDL Statements
    T visitCreateTableStatement(CreateTableStatementNode node);
    T visitDropTableStatement(DropTableStatementNode node);
    T visitAlterTableStatement(AlterTableStatementNode node);
    T visitCreateIndexStatement(CreateIndexStatementNode node);
    T visitDropIndexStatement(DropIndexStatementNode node);
    T visitCreateDatabaseStatement(CreateDatabaseStatementNode node);
    T visitDropDatabaseStatement(DropDatabaseStatementNode node);
    
    // DML Statements
    T visitInsertStatement(InsertStatementNode node);
    T visitUpdateStatement(UpdateStatementNode node);
    T visitDeleteStatement(DeleteStatementNode node);
    
    // DQL Statements
    T visitSelectStatement(SelectStatementNode node);
    
    // Transaction Statements
    T visitBeginTransaction(BeginTransactionNode node);
    T visitCommit(CommitNode node);
    T visitRollback(RollbackNode node);
    
    // Expressions
    T visitLiteral(LiteralNode node);
    T visitColumnReference(ColumnReferenceNode node);
    T visitBinaryOperation(BinaryOperationNode node);
    T visitUnaryOperation(UnaryOperationNode node);
    T visitFunctionCall(FunctionCallNode node);
    T visitCaseExpression(CaseExpressionNode node);
    T visitSubquery(SubqueryNode node);
    
    // Other
    T visitColumnDefinition(ColumnDefinitionNode node);
    T visitTableConstraint(TableConstraintNode node);
    T visitPlaceholder(PlaceholderNode node);
}
