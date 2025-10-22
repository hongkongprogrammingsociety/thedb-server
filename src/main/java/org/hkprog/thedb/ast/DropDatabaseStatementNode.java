package org.hkprog.thedb.ast;

public class DropDatabaseStatementNode implements ASTNode {
    private final String databaseName;
    private final boolean ifExists;
    
    public DropDatabaseStatementNode(String databaseName, boolean ifExists) {
        this.databaseName = databaseName;
        this.ifExists = ifExists;
    }
    
    public String getDatabaseName() { return databaseName; }
    public boolean isIfExists() { return ifExists; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitDropDatabaseStatement(this);
    }
    
    @Override
    public NodeType getNodeType() { return NodeType.DROP_DATABASE; }
}
