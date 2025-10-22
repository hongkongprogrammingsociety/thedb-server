package org.hkprog.thedb.ast;

import java.util.List;

/**
 * Represents a CREATE TABLE statement
 */
public class CreateTableStatementNode implements ASTNode {
    
    private final String tableName;
    private final List<ColumnDefinitionNode> columns;
    private final List<TableConstraintNode> constraints;
    private final boolean ifNotExists;
    
    public CreateTableStatementNode(String tableName, List<ColumnDefinitionNode> columns, 
                                    List<TableConstraintNode> constraints, boolean ifNotExists) {
        this.tableName = tableName;
        this.columns = columns;
        this.constraints = constraints;
        this.ifNotExists = ifNotExists;
    }
    
    public String getTableName() { return tableName; }
    public List<ColumnDefinitionNode> getColumns() { return columns; }
    public List<TableConstraintNode> getConstraints() { return constraints; }
    public boolean isIfNotExists() { return ifNotExists; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitCreateTableStatement(this);
    }
    
    @Override
    public NodeType getNodeType() {
        return NodeType.CREATE_TABLE;
    }
}
