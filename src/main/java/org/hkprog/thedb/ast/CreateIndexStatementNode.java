package org.hkprog.thedb.ast;

import java.util.List;

public class CreateIndexStatementNode implements ASTNode {
    private final String indexName;
    private final String tableName;
    private final List<String> columns;
    private final boolean unique;
    
    public CreateIndexStatementNode(String indexName, String tableName, List<String> columns, boolean unique) {
        this.indexName = indexName;
        this.tableName = tableName;
        this.columns = columns;
        this.unique = unique;
    }
    
    public String getIndexName() { return indexName; }
    public String getTableName() { return tableName; }
    public List<String> getColumns() { return columns; }
    public boolean isUnique() { return unique; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitCreateIndexStatement(this);
    }
    
    @Override
    public NodeType getNodeType() { return NodeType.CREATE_INDEX; }
}
