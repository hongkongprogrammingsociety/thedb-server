package org.hkprog.thedb.ast;

import java.util.List;

/**
 * Represents a SELECT statement
 */
public class SelectStatementNode implements ASTNode {
    
    private final boolean distinct;
    private final List<SelectElement> selectElements;
    private final List<TableSource> fromTables;
    private final List<JoinClause> joins;
    private final ASTNode whereClause;
    private final List<ASTNode> groupBy;
    private final ASTNode havingClause;
    private final List<OrderByElement> orderBy;
    private final Integer limit;
    private final Integer offset;
    
    public SelectStatementNode(
            boolean distinct,
            List<SelectElement> selectElements,
            List<TableSource> fromTables,
            List<JoinClause> joins,
            ASTNode whereClause,
            List<ASTNode> groupBy,
            ASTNode havingClause,
            List<OrderByElement> orderBy,
            Integer limit,
            Integer offset) {
        this.distinct = distinct;
        this.selectElements = selectElements;
        this.fromTables = fromTables;
        this.joins = joins;
        this.whereClause = whereClause;
        this.groupBy = groupBy;
        this.havingClause = havingClause;
        this.orderBy = orderBy;
        this.limit = limit;
        this.offset = offset;
    }
    
    // Getters
    public boolean isDistinct() { return distinct; }
    public List<SelectElement> getSelectElements() { return selectElements; }
    public List<TableSource> getFromTables() { return fromTables; }
    public List<JoinClause> getJoins() { return joins; }
    public ASTNode getWhereClause() { return whereClause; }
    public List<ASTNode> getGroupBy() { return groupBy; }
    public ASTNode getHavingClause() { return havingClause; }
    public List<OrderByElement> getOrderBy() { return orderBy; }
    public Integer getLimit() { return limit; }
    public Integer getOffset() { return offset; }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitSelectStatement(this);
    }
    
    @Override
    public NodeType getNodeType() {
        return NodeType.SELECT;
    }
    
    public static class SelectElement {
        private final ASTNode expression;
        private final String alias;
        
        public SelectElement(ASTNode expression, String alias) {
            this.expression = expression;
            this.alias = alias;
        }
        
        public ASTNode getExpression() { return expression; }
        public String getAlias() { return alias; }
    }
    
    public static class TableSource {
        private final String tableName;
        private final String alias;
        private final SelectStatementNode subquery;
        
        public TableSource(String tableName, String alias) {
            this.tableName = tableName;
            this.alias = alias;
            this.subquery = null;
        }
        
        public TableSource(SelectStatementNode subquery, String alias) {
            this.tableName = null;
            this.alias = alias;
            this.subquery = subquery;
        }
        
        public String getTableName() { return tableName; }
        public String getAlias() { return alias; }
        public SelectStatementNode getSubquery() { return subquery; }
        public boolean isSubquery() { return subquery != null; }
    }
    
    public static class JoinClause {
        private final JoinType joinType;
        private final TableSource tableSource;
        private final ASTNode onCondition;
        
        public enum JoinType {
            INNER, LEFT, RIGHT, FULL, CROSS, NATURAL
        }
        
        public JoinClause(JoinType joinType, TableSource tableSource, ASTNode onCondition) {
            this.joinType = joinType;
            this.tableSource = tableSource;
            this.onCondition = onCondition;
        }
        
        public JoinType getJoinType() { return joinType; }
        public TableSource getTableSource() { return tableSource; }
        public ASTNode getOnCondition() { return onCondition; }
    }
    
    public static class OrderByElement {
        private final ASTNode expression;
        private final boolean ascending;
        
        public OrderByElement(ASTNode expression, boolean ascending) {
            this.expression = expression;
            this.ascending = ascending;
        }
        
        public ASTNode getExpression() { return expression; }
        public boolean isAscending() { return ascending; }
    }
}
