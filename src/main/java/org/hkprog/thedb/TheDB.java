package org.hkprog.thedb;

import org.hkprog.thedb.server.TheDBServer;
import org.hkprog.thedb.compiler.SQLCompiler;
import org.hkprog.thedb.console.InteractiveConsole;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Main entry point for TheDB database server
 */
public class TheDB {
    
    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
            return;
        }
        
        try {
            String command = args[0];
            
            switch (command) {
                case "server":
                    int port = 3333;
                    String dataDir = "./data";
                    
                    for (int i = 1; i < args.length; i++) {
                        if (args[i].equals("--port") && i + 1 < args.length) {
                            port = Integer.parseInt(args[++i]);
                        } else if (args[i].equals("--data-dir") && i + 1 < args.length) {
                            dataDir = args[++i];
                        }
                    }
                    
                    System.out.println("Starting TheDB Server on port " + port);
                    System.out.println("Data directory: " + dataDir);
                    TheDBServer server = new TheDBServer(port, dataDir);
                    server.start();
                    break;
                    
                case "execute":
                    if (args.length < 2) {
                        System.err.println("Usage: thedb execute <script.sql>");
                        return;
                    }
                    String sqlFile = args[1];
                    String sqlContent = Files.readString(Paths.get(sqlFile));
                    SQLCompiler.executeScript(sqlContent);
                    break;
                    
                case "console":
                    System.out.println("TheDB Interactive Console");
                    System.out.println("Type 'exit' or 'quit' to exit");
                    InteractiveConsole console = new InteractiveConsole();
                    console.start();
                    break;
                    
                case "validate":
                    if (args.length < 2) {
                        System.err.println("Usage: thedb validate <script.sql>");
                        return;
                    }
                    String validateFile = args[1];
                    String validateContent = Files.readString(Paths.get(validateFile));
                    SQLCompiler.validateSyntax(validateContent);
                    System.out.println("SQL syntax is valid");
                    break;
                    
                case "version":
                    System.out.println("TheDB Server Version 0.0.1");
                    System.out.println("A MySQL-like relational database written in Java");
                    break;
                    
                case "help":
                    printUsage();
                    break;
                    
                default:
                    System.err.println("Unknown command: " + command);
                    printUsage();
                    System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    private static void printUsage() {
        System.out.println("TheDB - A MySQL-like Relational Database");
        System.out.println();
        System.out.println("Usage: thedb <command> [options]");
        System.out.println();
        System.out.println("Commands:");
        System.out.println("  server [--port 3306] [--data-dir ./data]  Start database server");
        System.out.println("  execute <script.sql>                       Execute SQL script file");
        System.out.println("  console                                    Start interactive console");
        System.out.println("  validate <script.sql>                      Validate SQL syntax");
        System.out.println("  version                                    Show version information");
        System.out.println("  help                                       Show this help message");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  thedb server --port 3307 --data-dir /var/thedb");
        System.out.println("  thedb execute example/create_tables.sql");
        System.out.println("  thedb console");
        System.out.println("  thedb validate queries.sql");
        System.out.println();
        System.out.println("TheDB supports standard SQL syntax including:");
        System.out.println("- DDL: CREATE TABLE, DROP TABLE, ALTER TABLE, CREATE INDEX");
        System.out.println("- DML: INSERT, UPDATE, DELETE");
        System.out.println("- DQL: SELECT with JOINs, GROUP BY, ORDER BY, LIMIT");
        System.out.println("- Transactions: BEGIN, COMMIT, ROLLBACK");
    }
}
