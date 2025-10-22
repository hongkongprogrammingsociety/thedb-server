package org.hkprog.thedb.ast;

public class CommitNode implements ASTNode {
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitCommit(this);
    }
    
    @Override
    public NodeType getNodeType() { return NodeType.COMMIT; }
}
