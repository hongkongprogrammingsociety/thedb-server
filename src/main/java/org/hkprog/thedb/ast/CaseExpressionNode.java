package org.hkprog.thedb.ast;

import java.util.List;

public class CaseExpressionNode implements ASTNode {
    private final ASTNode caseValue;
    private final List<WhenClause> whenClauses;
    private final ASTNode elseValue;
    
    public static class WhenClause {
        private final ASTNode condition;
        private final ASTNode result;
        
        public WhenClause(ASTNode condition, ASTNode result) {
            this.condition = condition;
            this.result = result;
        }
        
        public ASTNode getCondition() { return condition; }
        public ASTNode getResult() { return result; }
    }
    
    public CaseExpressionNode(ASTNode caseValue, List<WhenClause> whenClauses, ASTNode elseValue) {
        this.caseValue = caseValue;
        this.whenClauses = whenClauses;
        this.elseValue = elseValue;
    }
    
    public ASTNode getCaseValue() { return caseValue; }
    public List<WhenClause> getWhenClauses() { return whenClauses; }
    public ASTNode getElseValue() { return elseValue; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitCaseExpression(this);
    }
    
    @Override
    public NodeType getNodeType() { return NodeType.CASE_EXPRESSION; }
}
