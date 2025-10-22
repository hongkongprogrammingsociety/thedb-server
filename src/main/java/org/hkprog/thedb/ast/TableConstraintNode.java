package org.hkprog.thedb.ast;

import java.util.List;

/**
 * Represents a table constraint (PRIMARY KEY, FOREIGN KEY, UNIQUE, CHECK)
 */
public class TableConstraintNode implements ASTNode {
    
    private final ConstraintType type;
    private final String name;
    private final List<String> columns;
    private final String referencedTable;
    private final List<String> referencedColumns;
    private final ASTNode checkExpression;
    
    public enum ConstraintType {
        PRIMARY_KEY, FOREIGN_KEY, UNIQUE, CHECK
    }
    
    public TableConstraintNode(ConstraintType type, String name, List<String> columns,
                              String referencedTable, List<String> referencedColumns,
                              ASTNode checkExpression) {
        this.type = type;
        this.name = name;
        this.columns = columns;
        this.referencedTable = referencedTable;
        this.referencedColumns = referencedColumns;
        this.checkExpression = checkExpression;
    }
    
    public ConstraintType getType() { return type; }
    public String getName() { return name; }
    public List<String> getColumns() { return columns; }
    public String getReferencedTable() { return referencedTable; }
    public List<String> getReferencedColumns() { return referencedColumns; }
    public ASTNode getCheckExpression() { return checkExpression; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitTableConstraint(this);
    }
    
    @Override
    public NodeType getNodeType() {
        return NodeType.TABLE_CONSTRAINT;
    }
}
