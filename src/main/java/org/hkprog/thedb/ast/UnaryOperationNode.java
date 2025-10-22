package org.hkprog.thedb.ast;

/**
 * Represents a unary operation (e.g., NOT expr, -value)
 */
public class UnaryOperationNode implements ASTNode {
    
    private final Operator operator;
    private final ASTNode operand;
    
    public enum Operator {
        NOT,
        NEGATE,
        IS_NULL,
        IS_NOT_NULL
    }
    
    public UnaryOperationNode(Operator operator, ASTNode operand) {
        this.operator = operator;
        this.operand = operand;
    }
    
    public Operator getOperator() {
        return operator;
    }
    
    public ASTNode getOperand() {
        return operand;
    }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitUnaryOperation(this);
    }
    
    @Override
    public NodeType getNodeType() {
        return NodeType.UNARY_OPERATION;
    }
    
    @Override
    public String toString() {
        return "UnaryOp{" + operator + " " + operand + "}";
    }
}
