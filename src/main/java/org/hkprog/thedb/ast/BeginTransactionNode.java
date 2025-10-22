package org.hkprog.thedb.ast;

public class BeginTransactionNode implements ASTNode {
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitBeginTransaction(this);
    }
    
    @Override
    public NodeType getNodeType() { return NodeType.BEGIN_TRANSACTION; }
}
