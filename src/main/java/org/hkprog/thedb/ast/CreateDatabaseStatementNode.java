package org.hkprog.thedb.ast;

public class CreateDatabaseStatementNode implements ASTNode {
    private final String databaseName;
    private final boolean ifNotExists;
    
    public CreateDatabaseStatementNode(String databaseName, boolean ifNotExists) {
        this.databaseName = databaseName;
        this.ifNotExists = ifNotExists;
    }
    
    public String getDatabaseName() { return databaseName; }
    public boolean isIfNotExists() { return ifNotExists; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitCreateDatabaseStatement(this);
    }
    
    @Override
    public NodeType getNodeType() { return NodeType.CREATE_DATABASE; }
}
