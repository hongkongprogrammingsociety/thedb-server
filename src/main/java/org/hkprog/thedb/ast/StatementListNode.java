package org.hkprog.thedb.ast;

import java.util.List;

/**
 * Represents a list of SQL statements (a script/batch)
 */
public class StatementListNode implements ASTNode {
    
    private final List<ASTNode> statements;
    
    public StatementListNode(List<ASTNode> statements) {
        this.statements = statements;
    }
    
    public List<ASTNode> getStatements() {
        return statements;
    }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitStatementList(this);
    }
    
    @Override
    public NodeType getNodeType() {
        return NodeType.STATEMENT_LIST;
    }
}
