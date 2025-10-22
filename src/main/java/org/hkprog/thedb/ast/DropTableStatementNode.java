package org.hkprog.thedb.ast;

import java.util.List;

public class DropTableStatementNode implements ASTNode {
    private final List<String> tableNames;
    private final boolean ifExists;
    
    public DropTableStatementNode(List<String> tableNames, boolean ifExists) {
        this.tableNames = tableNames;
        this.ifExists = ifExists;
    }
    
    public List<String> getTableNames() { return tableNames; }
    public boolean isIfExists() { return ifExists; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitDropTableStatement(this);
    }
    
    @Override
    public NodeType getNodeType() { return NodeType.DROP_TABLE; }
}
