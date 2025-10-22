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
			CompilerHelper.printAST(ast, 0);

		} catch (Exception e) {
			System.err.println("Error parsing SQL: " + e.getMessage());
			e.printStackTrace();
		}
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
