package org.hkprog.thedb.ast;

import java.util.Map;

/**
 * Represents an UPDATE statement
 */
public class UpdateStatementNode implements ASTNode {
    
    private final String tableName;
    private final Map<String, ASTNode> assignments;
    private final ASTNode whereClause;
    
    public UpdateStatementNode(String tableName, Map<String, ASTNode> assignments, ASTNode whereClause) {
        this.tableName = tableName;
        this.assignments = assignments;
        this.whereClause = whereClause;
    }
    
    public String getTableName() {
        return tableName;
    }
    
    public Map<String, ASTNode> getAssignments() {
        return assignments;
    }
    
    public ASTNode getWhereClause() {
        return whereClause;
    }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitUpdateStatement(this);
    }
    
    @Override
    public NodeType getNodeType() {
        return NodeType.UPDATE;
    }
    
    @Override
    public String toString() {
        return "Update{table=" + tableName + ", assignments=" + assignments.size() + "}";
    }
}
