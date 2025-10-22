package org.hkprog.thedb.ast;

public class RollbackNode implements ASTNode {
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitRollback(this);
    }
    
    @Override
    public NodeType getNodeType() { return NodeType.ROLLBACK; }
}
