package org.hkprog.thedb.compiler;

import org.hkprog.thedb.ast.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Compiler Helper - utility functions for AST manipulation and printing
 */
public class CompilerHelper {

	/**
	 * Dump AST tree structure to console with tree visualization
	 */
	public static void dump(ASTNode root) {
		System.out.println("\n=== AST Dump ===");
		dumpNode(root, "", true);
		System.out.println("================\n");
	}

	/**
	 * Recursively dump AST nodes with tree visualization
	 */
	private static void dumpNode(ASTNode node, String prefix, boolean isLast) {
		if (node == null) {
			System.out.println(prefix + (isLast ? "└── " : "├── ") + "null");
			return;
		}

		// Print current node with tree connector
		String connector = isLast ? "└── " : "├── ";
		String nodeInfo = getNodeInfo(node);
		System.out.println((prefix + connector + nodeInfo).stripTrailing());

		// Prepare prefix for children
		String childPrefix = prefix + (isLast ? "    " : "│   ");

		// Get and print children based on node type
		List<ASTNode> children = getChildren(node);
		if (children != null && !children.isEmpty()) {
			for (int i = 0; i < children.size(); i++) {
				boolean childIsLast = (i == children.size() - 1);
				dumpNode(children.get(i), childPrefix, childIsLast);
			}
		}
	}

	/**
	 * Get node information string with position and specific details
	 */
	private static String getNodeInfo(ASTNode node) {
		String className = node.getClass().getSimpleName();

		if (node instanceof LiteralNode) {
			LiteralNode lit = (LiteralNode) node;
			return className + " = " + lit.getValue();
		} else if (node instanceof ColumnReferenceNode) {
			ColumnReferenceNode col = (ColumnReferenceNode) node;
			return className + " (" + (col.getTableName() != null ? col.getTableName() + "." : "") + col.getColumnName() + ")";
		} else if (node instanceof BinaryOperationNode) {
			BinaryOperationNode bin = (BinaryOperationNode) node;
			return className + " (op: " + bin.getOperator() + ")";
		} else if (node instanceof UnaryOperationNode) {
			UnaryOperationNode unary = (UnaryOperationNode) node;
			return className + " (op: " + unary.getOperator() + ")";
		} else if (node instanceof FunctionCallNode) {
			FunctionCallNode func = (FunctionCallNode) node;
			return className + " (" + func.getFunctionName() + ", args: " + func.getArguments().size() + ")";
		} else if (node instanceof SelectStatementNode) {
			SelectStatementNode select = (SelectStatementNode) node;
			return className + (select.isDistinct() ? " DISTINCT" : "") + " (elements: " + select.getSelectElements().size() + ")";
		} else if (node instanceof InsertStatementNode) {
			InsertStatementNode insert = (InsertStatementNode) node;
			return className + " (table: " + insert.getTableName() + ")";
		} else if (node instanceof UpdateStatementNode) {
			UpdateStatementNode update = (UpdateStatementNode) node;
			return className + " (table: " + update.getTableName() + ")";
		} else if (node instanceof DeleteStatementNode) {
			DeleteStatementNode delete = (DeleteStatementNode) node;
			return className + " (table: " + delete.getTableName() + ")";
		} else if (node instanceof CreateTableStatementNode) {
			CreateTableStatementNode create = (CreateTableStatementNode) node;
			return className + " (table: " + create.getTableName() + ")";
		}

		return className;
	}

	/**
	 * Get children of a node
	 */
	private static List<ASTNode> getChildren(ASTNode node) {
		List<ASTNode> children = new ArrayList<>();

		if (node instanceof SelectStatementNode) {
			SelectStatementNode select = (SelectStatementNode) node;
			for (SelectStatementNode.SelectElement elem : select.getSelectElements()) {
				children.add(elem.getExpression());
			}
			// Add FROM tables as children
			if (select.getFromTables() != null) {
				for (SelectStatementNode.TableSource table : select.getFromTables()) {
					if (table.isSubquery()) {
						children.add(table.getSubquery());
					}
				}
			}
			// Add JOINs as children
			if (select.getJoins() != null) {
				for (SelectStatementNode.JoinClause join : select.getJoins()) {
					if (join.getOnCondition() != null) {
						children.add(join.getOnCondition());
					}
					if (join.getTableSource().isSubquery()) {
						children.add(join.getTableSource().getSubquery());
					}
				}
			}
			if (select.getWhereClause() != null) {
				children.add(select.getWhereClause());
			}
			if (select.getHavingClause() != null) {
				children.add(select.getHavingClause());
			}
			if (select.getGroupBy() != null) {
				children.addAll(select.getGroupBy());
			}
			if (select.getOrderBy() != null) {
				for (SelectStatementNode.OrderByElement orderBy : select.getOrderBy()) {
					children.add(orderBy.getExpression());
				}
			}
		} else if (node instanceof BinaryOperationNode) {
			BinaryOperationNode bin = (BinaryOperationNode) node;
			children.add(bin.getLeft());
			children.add(bin.getRight());
		} else if (node instanceof UnaryOperationNode) {
			UnaryOperationNode unary = (UnaryOperationNode) node;
			children.add(unary.getOperand());
		} else if (node instanceof FunctionCallNode) {
			FunctionCallNode func = (FunctionCallNode) node;
			children.addAll(func.getArguments());
		} else if (node instanceof InsertStatementNode) {
			InsertStatementNode insert = (InsertStatementNode) node;
			if (insert.getValuesList() != null) {
				for (List<ASTNode> values : insert.getValuesList()) {
					children.addAll(values);
				}
			}
		} else if (node instanceof UpdateStatementNode) {
			UpdateStatementNode update = (UpdateStatementNode) node;
			// Add assignment expressions
			if (update.getAssignments() != null) {
				children.addAll(update.getAssignments().values());
			}
			if (update.getWhereClause() != null) {
				children.add(update.getWhereClause());
			}
		} else if (node instanceof DeleteStatementNode) {
			DeleteStatementNode delete = (DeleteStatementNode) node;
			if (delete.getWhereClause() != null) {
				children.add(delete.getWhereClause());
			}
		} else if (node instanceof CreateTableStatementNode) {
			CreateTableStatementNode create = (CreateTableStatementNode) node;
			// Add column definitions and constraints
			if (create.getColumns() != null) {
				children.addAll(create.getColumns());
			}
			if (create.getConstraints() != null) {
				children.addAll(create.getConstraints());
			}
		}

		return children;
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

	/**
	 * Print AST tree with indentation (legacy method)
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
}
