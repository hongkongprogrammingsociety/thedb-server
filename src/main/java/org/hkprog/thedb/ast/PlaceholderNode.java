package org.hkprog.thedb.ast;

/**
 * Placeholder node for statements not yet fully implemented
 */
public class PlaceholderNode implements ASTNode {
    private final String description;
    
    public PlaceholderNode(String description) {
        this.description = description;
    }
    
    public String getDescription() { return description; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitPlaceholder(this);
    }
    
    @Override
    public NodeType getNodeType() { return NodeType.PLACEHOLDER; }
}
