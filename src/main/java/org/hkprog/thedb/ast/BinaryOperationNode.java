package org.hkprog.thedb.ast;

/**
 * Represents a binary operation (e.g., a + b, x > 5, name = 'John')
 */
public class BinaryOperationNode implements ASTNode {
    
    private final ASTNode left;
    private final Operator operator;
    private final ASTNode right;
    
    public enum Operator {
        // Arithmetic
        ADD, SUBTRACT, MULTIPLY, DIVIDE, MODULO,
        
        // Comparison
        EQUAL, NOT_EQUAL, LESS_THAN, LESS_EQUAL, GREATER_THAN, GREATER_EQUAL,
        
        // Logical
        AND, OR,
        
        // Other
        LIKE, IN, BETWEEN
    }
    
    public BinaryOperationNode(ASTNode left, Operator operator, ASTNode right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
    
    public ASTNode getLeft() {
        return left;
    }
    
    public Operator getOperator() {
        return operator;
    }
    
    public ASTNode getRight() {
        return right;
    }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitBinaryOperation(this);
    }
    
    @Override
    public NodeType getNodeType() {
        return NodeType.BINARY_OPERATION;
    }
    
    @Override
    public String toString() {
        return "BinaryOp{" + left + " " + operator + " " + right + "}";
    }
}
