package org.hkprog.thedb.compiler;

import org.hkprog.thedb.ast.*;
import org.hkprog.thedb.antlr.*;
import java.util.*;

/**
 * AST Builder - converts ANTLR parse tree to AST
 */
public class SQLASTBuilder extends SQLParserBaseVisitor<ASTNode> {
    
    @Override
    public ASTNode visitSqlStatements(SQLParser.SqlStatementsContext ctx) {
        if (ctx.sqlStatement() != null && !ctx.sqlStatement().isEmpty()) {
            ASTNode result = visit(ctx.sqlStatement(0));
            if (result != null) {
                System.out.println(">>>" + result.getClass().getSimpleName());
            }
            return result;
        }
        return null;
    }
    
    @Override
    public ASTNode visitDdlStatement(SQLParser.DdlStatementContext ctx) {
        // For now, return a placeholder node for DDL statements
        // TODO: Implement proper AST nodes for CREATE TABLE, DROP TABLE, etc.
        return new PlaceholderNode("DDL: " + ctx.getText());
    }
    
    @Override
    public ASTNode visitUtilityStatement(SQLParser.UtilityStatementContext ctx) {
        // For now, return a placeholder node for utility statements
        // TODO: Implement proper AST nodes for SHOW TABLES, DESCRIBE, etc.
        return new PlaceholderNode("UTILITY: " + ctx.getText());
    }
    
    @Override
    public ASTNode visitSelectStatement(SQLParser.SelectStatementContext ctx) {
        boolean distinct = ctx.DISTINCT() != null;
        
        List<SelectStatementNode.SelectElement> elements = new ArrayList<>();
        if (ctx.selectElements().MULTIPLY() != null) {
            elements.add(new SelectStatementNode.SelectElement(
                new ColumnReferenceNode(null, "*"), null));
        } else {
            for (SQLParser.SelectElementContext elemCtx : ctx.selectElements().selectElement()) {
                ASTNode expr = visit(elemCtx.expression());
                String alias = elemCtx.columnAlias() != null ? elemCtx.columnAlias().getText() : null;
                elements.add(new SelectStatementNode.SelectElement(expr, alias));
            }
        }
        
        List<SelectStatementNode.TableSource> tables = new ArrayList<>();
        if (ctx.tableSource() != null && ctx.tableSource().tableName() != null) {
            String tableName = ctx.tableSource().tableName().getText();
            String alias = ctx.tableSource().tableAlias() != null ? ctx.tableSource().tableAlias().getText() : null;
            tables.add(new SelectStatementNode.TableSource(tableName, alias));
        }
        
        ASTNode whereClause = ctx.whereExpression != null ? visit(ctx.whereExpression) : null;
        
        List<ASTNode> groupBy = new ArrayList<>();
        if (ctx.groupByItem() != null) {
            for (SQLParser.GroupByItemContext item : ctx.groupByItem()) {
                groupBy.add(visit(item.expression()));
            }
        }
        
        ASTNode havingClause = ctx.havingExpression != null ? visit(ctx.havingExpression) : null;
        
        List<SelectStatementNode.OrderByElement> orderBy = new ArrayList<>();
        if (ctx.orderByItem() != null) {
            for (SQLParser.OrderByItemContext item : ctx.orderByItem()) {
                ASTNode expr = visit(item.expression());
                boolean asc = item.DESC() == null;
                orderBy.add(new SelectStatementNode.OrderByElement(expr, asc));
            }
        }
        
        Integer limit = ctx.limitCount != null ? Integer.parseInt(ctx.limitCount.getText()) : null;
        Integer offset = ctx.offsetCount != null ? Integer.parseInt(ctx.offsetCount.getText()) : null;
        
        return new SelectStatementNode(distinct, elements, tables, new ArrayList<>(), 
                                      whereClause, groupBy, havingClause, orderBy, limit, offset);
    }
    
    @Override
    public ASTNode visitInsertStatement(SQLParser.InsertStatementContext ctx) {
        String tableName = ctx.tableName().getText();
        List<String> columns = new ArrayList<>();
        if (ctx.columnName() != null) {
            for (SQLParser.ColumnNameContext colCtx : ctx.columnName()) {
                columns.add(colCtx.getText());
            }
        }
        List<List<ASTNode>> valuesList = new ArrayList<>();
        for (SQLParser.ValuesListContext listCtx : ctx.valuesList()) {
            List<ASTNode> values = new ArrayList<>();
            for (SQLParser.ExpressionContext exprCtx : listCtx.expression()) {
                values.add(visit(exprCtx));
            }
            valuesList.add(values);
        }
        return new InsertStatementNode(tableName, columns, valuesList);
    }
    
    @Override
    public ASTNode visitUpdateStatement(SQLParser.UpdateStatementContext ctx) {
        String tableName = ctx.tableName().getText();
        Map<String, ASTNode> assignments = new LinkedHashMap<>();
        List<SQLParser.ColumnNameContext> columns = ctx.columnName();
        List<SQLParser.ExpressionContext> expressions = ctx.expression();
        int numAssignments = ctx.WHERE() != null ? expressions.size() - 1 : expressions.size();
        for (int i = 0; i < numAssignments; i++) {
            assignments.put(columns.get(i).getText(), visit(expressions.get(i)));
        }
        ASTNode whereClause = ctx.WHERE() != null ? visit(expressions.get(expressions.size() - 1)) : null;
        return new UpdateStatementNode(tableName, assignments, whereClause);
    }
    
    @Override
    public ASTNode visitDeleteStatement(SQLParser.DeleteStatementContext ctx) {
        String tableName = ctx.tableName().getText();
        ASTNode whereClause = ctx.WHERE() != null && ctx.expression() != null ? visit(ctx.expression()) : null;
        return new DeleteStatementNode(tableName, whereClause);
    }
    
    @Override
    public ASTNode visitLiteral(SQLParser.LiteralContext ctx) {
        if (ctx.INTEGER_LITERAL() != null) {
            return new LiteralNode(Integer.parseInt(ctx.INTEGER_LITERAL().getText()), LiteralNode.LiteralType.INTEGER);
        } else if (ctx.DECIMAL_LITERAL() != null) {
            return new LiteralNode(Double.parseDouble(ctx.DECIMAL_LITERAL().getText()), LiteralNode.LiteralType.DECIMAL);
        } else if (ctx.STRING_LITERAL() != null) {
            String text = ctx.STRING_LITERAL().getText();
            return new LiteralNode(text.substring(1, text.length() - 1), LiteralNode.LiteralType.STRING);
        } else if (ctx.TRUE() != null) {
            return new LiteralNode(true, LiteralNode.LiteralType.BOOLEAN);
        } else if (ctx.FALSE() != null) {
            return new LiteralNode(false, LiteralNode.LiteralType.BOOLEAN);
        } else if (ctx.NULL() != null) {
            return new LiteralNode(null, LiteralNode.LiteralType.NULL);
        }
        return null;
    }
    
    @Override
    public ASTNode visitColumnReference(SQLParser.ColumnReferenceContext ctx) {
        String tableName = ctx.tableName() != null ? ctx.tableName().getText() : null;
        String columnName = ctx.columnName().getText();
        return new ColumnReferenceNode(tableName, columnName);
    }
    
    private BinaryOperationNode.Operator parseOperator(String op) {
        switch (op.toUpperCase()) {
            case "+": return BinaryOperationNode.Operator.ADD;
            case "-": return BinaryOperationNode.Operator.SUBTRACT;
            case "*": return BinaryOperationNode.Operator.MULTIPLY;
            case "/": return BinaryOperationNode.Operator.DIVIDE;
            case "%": return BinaryOperationNode.Operator.MODULO;
            case "=": return BinaryOperationNode.Operator.EQUAL;
            case "<>": case "!=": return BinaryOperationNode.Operator.NOT_EQUAL;
            case "<": return BinaryOperationNode.Operator.LESS_THAN;
            case "<=": return BinaryOperationNode.Operator.LESS_EQUAL;
            case ">": return BinaryOperationNode.Operator.GREATER_THAN;
            case ">=": return BinaryOperationNode.Operator.GREATER_EQUAL;
            case "AND": return BinaryOperationNode.Operator.AND;
            case "OR": return BinaryOperationNode.Operator.OR;
            case "LIKE": return BinaryOperationNode.Operator.LIKE;
            default: return BinaryOperationNode.Operator.EQUAL;
        }
    }
    
    @Override
    public ASTNode visitAddExpression(SQLParser.AddExpressionContext ctx) {
        return new BinaryOperationNode(visit(ctx.expression(0)), BinaryOperationNode.Operator.ADD, visit(ctx.expression(1)));
    }
    
    @Override
    public ASTNode visitSubtractExpression(SQLParser.SubtractExpressionContext ctx) {
        return new BinaryOperationNode(visit(ctx.expression(0)), BinaryOperationNode.Operator.SUBTRACT, visit(ctx.expression(1)));
    }
    
    @Override
    public ASTNode visitMultiplyExpression(SQLParser.MultiplyExpressionContext ctx) {
        return new BinaryOperationNode(visit(ctx.expression(0)), BinaryOperationNode.Operator.MULTIPLY, visit(ctx.expression(1)));
    }
    
    @Override
    public ASTNode visitDivideExpression(SQLParser.DivideExpressionContext ctx) {
        return new BinaryOperationNode(visit(ctx.expression(0)), BinaryOperationNode.Operator.DIVIDE, visit(ctx.expression(1)));
    }
    
    @Override
    public ASTNode visitComparisonExpression(SQLParser.ComparisonExpressionContext ctx) {
        return new BinaryOperationNode(visit(ctx.expression(0)), parseOperator(ctx.comparisonOperator().getText()), visit(ctx.expression(1)));
    }
    
    @Override
    public ASTNode visitAndExpression(SQLParser.AndExpressionContext ctx) {
        return new BinaryOperationNode(visit(ctx.expression(0)), BinaryOperationNode.Operator.AND, visit(ctx.expression(1)));
    }
    
    @Override
    public ASTNode visitOrExpression(SQLParser.OrExpressionContext ctx) {
        return new BinaryOperationNode(visit(ctx.expression(0)), BinaryOperationNode.Operator.OR, visit(ctx.expression(1)));
    }
    
    @Override
    public ASTNode visitNotExpression(SQLParser.NotExpressionContext ctx) {
        return new UnaryOperationNode(UnaryOperationNode.Operator.NOT, visit(ctx.expression()));
    }
    
    @Override
    public ASTNode visitIsNullExpression(SQLParser.IsNullExpressionContext ctx) {
        UnaryOperationNode.Operator op = ctx.NOT() != null ? UnaryOperationNode.Operator.IS_NOT_NULL : UnaryOperationNode.Operator.IS_NULL;
        return new UnaryOperationNode(op, visit(ctx.expression()));
    }
    
    @Override
    public ASTNode visitFunctionCall(SQLParser.FunctionCallContext ctx) {
        String functionName = ctx.getChild(0).getText();
        List<ASTNode> arguments = new ArrayList<>();
        if (ctx.MULTIPLY() != null) {
            arguments.add(new ColumnReferenceNode(null, "*"));
        } else if (ctx.expression() != null) {
            for (SQLParser.ExpressionContext exprCtx : ctx.expression()) {
                arguments.add(visit(exprCtx));
            }
        }
        return new FunctionCallNode(functionName, arguments);
    }
    
    @Override
    public ASTNode visitParenExpression(SQLParser.ParenExpressionContext ctx) {
        return visit(ctx.expression());
    }
}
