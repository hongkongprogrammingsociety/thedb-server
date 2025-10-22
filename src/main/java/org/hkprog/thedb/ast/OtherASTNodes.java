package org.hkprog.thedb.ast;

import java.util.List;

/**
 * Stub implementations for remaining AST nodes
 */

class DropTableStatementNode implements ASTNode {
    private final List<String> tableNames;
    private final boolean ifExists;
    
    public DropTableStatementNode(List<String> tableNames, boolean ifExists) {
        this.tableNames = tableNames;
        this.ifExists = ifExists;
    }
    
    public List<String> getTableNames() { return tableNames; }
    public boolean isIfExists() { return ifExists; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitDropTableStatement(this);
    }
    
    @Override
    public NodeType getNodeType() { return NodeType.DROP_TABLE; }
}

class AlterTableStatementNode implements ASTNode {
    private final String tableName;
    private final List<AlterAction> actions;
    
    public static class AlterAction {
        public enum ActionType { ADD_COLUMN, DROP_COLUMN, MODIFY_COLUMN, ADD_CONSTRAINT, DROP_CONSTRAINT }
        private final ActionType type;
        private final Object data;
        
        public AlterAction(ActionType type, Object data) {
            this.type = type;
            this.data = data;
        }
        
        public ActionType getType() { return type; }
        public Object getData() { return data; }
    }
    
    public AlterTableStatementNode(String tableName, List<AlterAction> actions) {
        this.tableName = tableName;
        this.actions = actions;
    }
    
    public String getTableName() { return tableName; }
    public List<AlterAction> getActions() { return actions; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitAlterTableStatement(this);
    }
    
    @Override
    public NodeType getNodeType() { return NodeType.ALTER_TABLE; }
}

class CreateIndexStatementNode implements ASTNode {
    private final String indexName;
    private final String tableName;
    private final List<String> columns;
    private final boolean unique;
    
    public CreateIndexStatementNode(String indexName, String tableName, List<String> columns, boolean unique) {
        this.indexName = indexName;
        this.tableName = tableName;
        this.columns = columns;
        this.unique = unique;
    }
    
    public String getIndexName() { return indexName; }
    public String getTableName() { return tableName; }
    public List<String> getColumns() { return columns; }
    public boolean isUnique() { return unique; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitCreateIndexStatement(this);
    }
    
    @Override
    public NodeType getNodeType() { return NodeType.CREATE_INDEX; }
}

class DropIndexStatementNode implements ASTNode {
    private final String indexName;
    private final String tableName;
    
    public DropIndexStatementNode(String indexName, String tableName) {
        this.indexName = indexName;
        this.tableName = tableName;
    }
    
    public String getIndexName() { return indexName; }
    public String getTableName() { return tableName; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitDropIndexStatement(this);
    }
    
    @Override
    public NodeType getNodeType() { return NodeType.DROP_INDEX; }
}

class CreateDatabaseStatementNode implements ASTNode {
    private final String databaseName;
    private final boolean ifNotExists;
    
    public CreateDatabaseStatementNode(String databaseName, boolean ifNotExists) {
        this.databaseName = databaseName;
        this.ifNotExists = ifNotExists;
    }
    
    public String getDatabaseName() { return databaseName; }
    public boolean isIfNotExists() { return ifNotExists; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitCreateDatabaseStatement(this);
    }
    
    @Override
    public NodeType getNodeType() { return NodeType.CREATE_DATABASE; }
}

class DropDatabaseStatementNode implements ASTNode {
    private final String databaseName;
    private final boolean ifExists;
    
    public DropDatabaseStatementNode(String databaseName, boolean ifExists) {
        this.databaseName = databaseName;
        this.ifExists = ifExists;
    }
    
    public String getDatabaseName() { return databaseName; }
    public boolean isIfExists() { return ifExists; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitDropDatabaseStatement(this);
    }
    
    @Override
    public NodeType getNodeType() { return NodeType.DROP_DATABASE; }
}

class BeginTransactionNode implements ASTNode {
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitBeginTransaction(this);
    }
    
    @Override
    public NodeType getNodeType() { return NodeType.BEGIN_TRANSACTION; }
}

class CommitNode implements ASTNode {
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitCommit(this);
    }
    
    @Override
    public NodeType getNodeType() { return NodeType.COMMIT; }
}

class RollbackNode implements ASTNode {
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitRollback(this);
    }
    
    @Override
    public NodeType getNodeType() { return NodeType.ROLLBACK; }
}

class CaseExpressionNode implements ASTNode {
    private final ASTNode caseValue;
    private final List<WhenClause> whenClauses;
    private final ASTNode elseValue;
    
    public static class WhenClause {
        private final ASTNode condition;
        private final ASTNode result;
        
        public WhenClause(ASTNode condition, ASTNode result) {
            this.condition = condition;
            this.result = result;
        }
        
        public ASTNode getCondition() { return condition; }
        public ASTNode getResult() { return result; }
    }
    
    public CaseExpressionNode(ASTNode caseValue, List<WhenClause> whenClauses, ASTNode elseValue) {
        this.caseValue = caseValue;
        this.whenClauses = whenClauses;
        this.elseValue = elseValue;
    }
    
    public ASTNode getCaseValue() { return caseValue; }
    public List<WhenClause> getWhenClauses() { return whenClauses; }
    public ASTNode getElseValue() { return elseValue; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitCaseExpression(this);
    }
    
    @Override
    public NodeType getNodeType() { return NodeType.CASE_EXPRESSION; }
}

class SubqueryNode implements ASTNode {
    private final SelectStatementNode selectStatement;
    
    public SubqueryNode(SelectStatementNode selectStatement) {
        this.selectStatement = selectStatement;
    }
    
    public SelectStatementNode getSelectStatement() { return selectStatement; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitSubquery(this);
    }
    
    @Override
    public NodeType getNodeType() { return NodeType.SUBQUERY; }
}
