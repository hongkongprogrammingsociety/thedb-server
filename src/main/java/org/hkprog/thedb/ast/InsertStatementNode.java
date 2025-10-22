package org.hkprog.thedb.ast;

import java.util.List;
import java.util.Map;

/**
 * Represents an INSERT statement
 */
public class InsertStatementNode implements ASTNode {
    
    private final String tableName;
    private final List<String> columns;
    private final List<List<ASTNode>> valuesList;
    
    public InsertStatementNode(String tableName, List<String> columns, List<List<ASTNode>> valuesList) {
        this.tableName = tableName;
        this.columns = columns;
        this.valuesList = valuesList;
    }
    
    public String getTableName() {
        return tableName;
    }
    
    public List<String> getColumns() {
        return columns;
    }
    
    public List<List<ASTNode>> getValuesList() {
        return valuesList;
    }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitInsertStatement(this);
    }
    
    @Override
    public NodeType getNodeType() {
        return NodeType.INSERT;
    }
    
    @Override
    public String toString() {
        return "Insert{table=" + tableName + ", columns=" + columns + ", rows=" + valuesList.size() + "}";
    }
}
