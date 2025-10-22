package org.hkprog.thedb.ast;

import java.util.List;

/**
 * Represents a function call (e.g., COUNT(*), SUM(price), CONCAT(a, b))
 */
public class FunctionCallNode implements ASTNode {
    
    private final String functionName;
    private final List<ASTNode> arguments;
    private final boolean distinct;
    
    public FunctionCallNode(String functionName, List<ASTNode> arguments) {
        this(functionName, arguments, false);
    }
    
    public FunctionCallNode(String functionName, List<ASTNode> arguments, boolean distinct) {
        this.functionName = functionName;
        this.arguments = arguments;
        this.distinct = distinct;
    }
    
    public String getFunctionName() {
        return functionName;
    }
    
    public List<ASTNode> getArguments() {
        return arguments;
    }
    
    public boolean isDistinct() {
        return distinct;
    }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitFunctionCall(this);
    }
    
    @Override
    public NodeType getNodeType() {
        return NodeType.FUNCTION_CALL;
    }
    
    @Override
    public String toString() {
        return "Function{" + functionName + (distinct ? "(DISTINCT " : "(") + arguments + ")}";
    }
}
