package org.hkprog.thedb.ast;

public class SubqueryNode implements ASTNode {
    private final SelectStatementNode selectStatement;
    
    public SubqueryNode(SelectStatementNode selectStatement) {
        this.selectStatement = selectStatement;
    }
    
    public SelectStatementNode getSelectStatement() { return selectStatement; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitSubquery(this);
    }
    
    @Override
    public NodeType getNodeType() { return NodeType.SUBQUERY; }
}
