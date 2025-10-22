package org.hkprog.thedb.ast;

public class DropIndexStatementNode implements ASTNode {
    private final String indexName;
    private final String tableName;
    
    public DropIndexStatementNode(String indexName, String tableName) {
        this.indexName = indexName;
        this.tableName = tableName;
    }
    
    public String getIndexName() { return indexName; }
    public String getTableName() { return tableName; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitDropIndexStatement(this);
    }
    
    @Override
    public NodeType getNodeType() { return NodeType.DROP_INDEX; }
}
