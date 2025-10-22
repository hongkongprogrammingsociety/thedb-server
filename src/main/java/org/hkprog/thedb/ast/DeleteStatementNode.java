package org.hkprog.thedb.ast;

/**
 * Represents a DELETE statement
 */
public class DeleteStatementNode implements ASTNode {
    
    private final String tableName;
    private final ASTNode whereClause;
    
    public DeleteStatementNode(String tableName, ASTNode whereClause) {
        this.tableName = tableName;
        this.whereClause = whereClause;
    }
    
    public String getTableName() {
        return tableName;
    }
    
    public ASTNode getWhereClause() {
        return whereClause;
    }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitDeleteStatement(this);
    }
    
    @Override
    public NodeType getNodeType() {
        return NodeType.DELETE;
    }
    
    @Override
    public String toString() {
        return "Delete{table=" + tableName + "}";
    }
}
