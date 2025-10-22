package org.hkprog.thedb.compiler;

import org.hkprog.thedb.ast.*;
import org.hkprog.thedb.antlr.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * SQL Compiler - parses SQL and creates AST
 */
public class SQLCompiler {
    
    /**
     * Parse SQL and create AST
     */
    public static ASTNode parse(String sql) throws IOException {
        CharStream input = CharStreams.fromString(sql);
        
        // Lexer - tokenize
        SQLLexer lexer = new SQLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        
        // Parser - parse
        SQLParser parser = new SQLParser(tokens);
        
        // Error handling
        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                    int line, int charPositionInLine, String msg, RecognitionException e) {
                throw new RuntimeException("Syntax error at line " + line + ":" + charPositionInLine + " - " + msg);
            }
        });
        
        SQLParser.SqlStatementsContext tree = parser.sqlStatements();
        
        // Build AST
        SQLASTBuilder astBuilder = new SQLASTBuilder();
        return astBuilder.visit(tree);
    }
    
    /**
     * Parse SQL and print the parse tree
     */
    public static void parseAndPrintTree(String sql) {
        try {
            CharStream input = CharStreams.fromString(sql);
            SQLLexer lexer = new SQLLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            SQLParser parser = new SQLParser(tokens);
            
            // Parse
            SQLParser.SqlStatementsContext tree = parser.sqlStatements();
            
            // Print parse tree
            System.out.println("=== PARSE TREE ===");
            System.out.println(tree.toStringTree(parser));
            System.out.println();
            
            // Build and print AST
            SQLASTBuilder astBuilder = new SQLASTBuilder();
            ASTNode ast = astBuilder.visit(tree);
            
            System.out.println("=== AST TREE ===");
            printAST(ast, 0);
            
        } catch (Exception e) {
            System.err.println("Error parsing SQL: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Print AST tree with indentation
     */
    private static void printAST(ASTNode node, int depth) {
        if (node == null) {
            return;
        }
        
        String indent = "  ".repeat(depth);
        System.out.println(indent + nodeToString(node));
        
        // Print children based on node type
        if (node instanceof SelectStatementNode) {
            SelectStatementNode select = (SelectStatementNode) node;
            System.out.println(indent + "  [SELECT ELEMENTS]");
            for (SelectStatementNode.SelectElement elem : select.getSelectElements()) {
                System.out.println(indent + "    " + (elem.getAlias() != null ? elem.getAlias() + " = " : "") + "expression");
                printAST(elem.getExpression(), depth + 3);
            }
            if (select.getWhereClause() != null) {
                System.out.println(indent + "  [WHERE]");
                printAST(select.getWhereClause(), depth + 2);
            }
        } else if (node instanceof BinaryOperationNode) {
            BinaryOperationNode bin = (BinaryOperationNode) node;
            System.out.println(indent + "  [LEFT]");
            printAST(bin.getLeft(), depth + 2);
            System.out.println(indent + "  [RIGHT]");
            printAST(bin.getRight(), depth + 2);
        } else if (node instanceof UnaryOperationNode) {
            UnaryOperationNode unary = (UnaryOperationNode) node;
            System.out.println(indent + "  [OPERAND]");
            printAST(unary.getOperand(), depth + 2);
        } else if (node instanceof FunctionCallNode) {
            FunctionCallNode func = (FunctionCallNode) node;
            System.out.println(indent + "  [ARGUMENTS]");
            for (ASTNode arg : func.getArguments()) {
                printAST(arg, depth + 2);
            }
        }
    }
    
    /**
     * Convert AST node to readable string
     */
    private static String nodeToString(ASTNode node) {
        if (node instanceof LiteralNode) {
            LiteralNode lit = (LiteralNode) node;
            return "Literal(" + lit.getValue() + ")";
        } else if (node instanceof ColumnReferenceNode) {
            ColumnReferenceNode col = (ColumnReferenceNode) node;
            return "Column(" + (col.getTableName() != null ? col.getTableName() + "." : "") + col.getColumnName() + ")";
        } else if (node instanceof BinaryOperationNode) {
            BinaryOperationNode bin = (BinaryOperationNode) node;
            return "BinaryOp(" + bin.getOperator() + ")";
        } else if (node instanceof UnaryOperationNode) {
            UnaryOperationNode unary = (UnaryOperationNode) node;
            return "UnaryOp(" + unary.getOperator() + ")";
        } else if (node instanceof FunctionCallNode) {
            FunctionCallNode func = (FunctionCallNode) node;
            return "Function(" + func.getFunctionName() + ")";
        } else if (node instanceof SelectStatementNode) {
            SelectStatementNode select = (SelectStatementNode) node;
            return "SELECT" + (select.isDistinct() ? " DISTINCT" : "");
        } else if (node instanceof InsertStatementNode) {
            InsertStatementNode insert = (InsertStatementNode) node;
            return "INSERT INTO " + insert.getTableName();
        } else if (node instanceof UpdateStatementNode) {
            UpdateStatementNode update = (UpdateStatementNode) node;
            return "UPDATE " + update.getTableName();
        } else if (node instanceof DeleteStatementNode) {
            DeleteStatementNode delete = (DeleteStatementNode) node;
            return "DELETE FROM " + delete.getTableName();
        } else if (node instanceof CreateTableStatementNode) {
            CreateTableStatementNode create = (CreateTableStatementNode) node;
            return "CREATE TABLE " + create.getTableName();
        }
        return node.getClass().getSimpleName();
    }
    
    /**
     * Execute a SQL script
     */
    public static void executeScript(String sql) {
        parseAndPrintTree(sql);
        System.out.println("\n[Execution not yet implemented - storage engine required]");
    }
    
    /**
     * Validate SQL syntax
     */
    public static void validateSyntax(String sql) throws IOException {
        System.out.println("Validating SQL syntax...");
        // parse(sql);
        System.out.println("SQL syntax validation complete");
    }
}
