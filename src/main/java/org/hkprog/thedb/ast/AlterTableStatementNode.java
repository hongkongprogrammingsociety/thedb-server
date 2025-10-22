package org.hkprog.thedb.ast;

import java.util.List;

public class AlterTableStatementNode implements ASTNode {
    private final String tableName;
    private final List<AlterAction> actions;
    
    public static class AlterAction {
        public enum ActionType { ADD_COLUMN, DROP_COLUMN, MODIFY_COLUMN, ADD_CONSTRAINT, DROP_CONSTRAINT }
        private final ActionType type;
        private final Object data;
        
        public AlterAction(ActionType type, Object data) {
            this.type = type;
            this.data = data;
        }
        
        public ActionType getType() { return type; }
        public Object getData() { return data; }
    }
    
    public AlterTableStatementNode(String tableName, List<AlterAction> actions) {
        this.tableName = tableName;
        this.actions = actions;
    }
    
    public String getTableName() { return tableName; }
    public List<AlterAction> getActions() { return actions; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitAlterTableStatement(this);
    }
    
    @Override
    public NodeType getNodeType() { return NodeType.ALTER_TABLE; }
}
