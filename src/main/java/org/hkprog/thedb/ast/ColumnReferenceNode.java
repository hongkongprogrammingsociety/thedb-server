package org.hkprog.thedb.ast;

/**
 * Represents a column reference (e.g., table.column or just column)
 */
public class ColumnReferenceNode implements ASTNode {
    
    private final String tableName;
    private final String columnName;
    
    public ColumnReferenceNode(String columnName) {
        this(null, columnName);
    }
    
    public ColumnReferenceNode(String tableName, String columnName) {
        this.tableName = tableName;
        this.columnName = columnName;
    }
    
    public String getTableName() {
        return tableName;
    }
    
    public String getColumnName() {
        return columnName;
    }
    
    public boolean hasTableQualifier() {
        return tableName != null;
    }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitColumnReference(this);
    }
    
    @Override
    public NodeType getNodeType() {
        return NodeType.COLUMN_REFERENCE;
    }
    
    @Override
    public String toString() {
        return "ColumnRef{" + (tableName != null ? tableName + "." : "") + columnName + "}";
    }
}
