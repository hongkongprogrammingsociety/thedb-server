package org.hkprog.thedb.compiler;

import org.hkprog.thedb.ast.*;

/**
 * Compiler Helper - utility functions for AST manipulation and printing
 */
public class CompilerHelper {

	/**
	 * Print AST tree with indentation
	 */
	public static void printAST(ASTNode node, int depth) {
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
				System.out.println(
						indent + "    " + (elem.getAlias() != null ? elem.getAlias() + " = " : "") + "expression");
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
	public static String nodeToString(ASTNode node) {
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
}
