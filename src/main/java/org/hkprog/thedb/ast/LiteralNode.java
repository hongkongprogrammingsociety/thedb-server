package org.hkprog.thedb.ast;

/**
 * Represents a literal value (number, string, boolean, null)
 */
public class LiteralNode implements ASTNode {
    
    private final Object value;
    private final LiteralType type;
    
    public enum LiteralType {
        INTEGER,
        DECIMAL,
        STRING,
        BOOLEAN,
        NULL
    }
    
    public LiteralNode(Object value, LiteralType type) {
        this.value = value;
        this.type = type;
    }
    
    public Object getValue() {
        return value;
    }
    
    public LiteralType getLiteralType() {
        return type;
    }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitLiteral(this);
    }
    
    @Override
    public NodeType getNodeType() {
        return NodeType.LITERAL;
    }
    
    @Override
    public String toString() {
        return "Literal{" + type + "=" + value + "}";
    }
}
