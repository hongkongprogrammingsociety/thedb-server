package org.hkprog.thedb.ast;

import java.util.List;

/**
 * Represents a column definition in CREATE TABLE
 */
public class ColumnDefinitionNode implements ASTNode {
    
    private final String columnName;
    private final DataType dataType;
    private final List<ColumnConstraint> constraints;
    
    public static class DataType {
        private final String typeName;
        private final Integer length;
        private final Integer precision;
        private final Integer scale;
        
        public DataType(String typeName) {
            this(typeName, null, null, null);
        }
        
        public DataType(String typeName, Integer length) {
            this(typeName, length, null, null);
        }
        
        public DataType(String typeName, Integer precision, Integer scale) {
            this(typeName, null, precision, scale);
        }
        
        private DataType(String typeName, Integer length, Integer precision, Integer scale) {
            this.typeName = typeName;
            this.length = length;
            this.precision = precision;
            this.scale = scale;
        }
        
        public String getTypeName() { return typeName; }
        public Integer getLength() { return length; }
        public Integer getPrecision() { return precision; }
        public Integer getScale() { return scale; }
    }
    
    public static class ColumnConstraint {
        private final ConstraintType type;
        private final Object value;
        
        public enum ConstraintType {
            NOT_NULL, NULL, PRIMARY_KEY, UNIQUE, AUTO_INCREMENT, DEFAULT, FOREIGN_KEY
        }
        
        public ColumnConstraint(ConstraintType type) {
            this(type, null);
        }
        
        public ColumnConstraint(ConstraintType type, Object value) {
            this.type = type;
            this.value = value;
        }
        
        public ConstraintType getType() { return type; }
        public Object getValue() { return value; }
    }
    
    public ColumnDefinitionNode(String columnName, DataType dataType, List<ColumnConstraint> constraints) {
        this.columnName = columnName;
        this.dataType = dataType;
        this.constraints = constraints;
    }
    
    public String getColumnName() { return columnName; }
    public DataType getDataType() { return dataType; }
    public List<ColumnConstraint> getConstraints() { return constraints; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitColumnDefinition(this);
    }
    
    @Override
    public NodeType getNodeType() {
        return NodeType.COLUMN_DEFINITION;
    }
}
