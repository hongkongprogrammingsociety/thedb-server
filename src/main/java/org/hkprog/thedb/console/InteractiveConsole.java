package org.hkprog.thedb.console;

import java.io.*;

/**
 * Interactive SQL Console
 */
public class InteractiveConsole {
    
    private BufferedReader reader;
    private boolean running;
    
    public InteractiveConsole() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.running = false;
    }
    
    /**
     * Start the interactive console
     */
    public void start() throws IOException {
        running = true;
        
        while (running) {
            System.out.print("thedb> ");
            String line = reader.readLine();
            
            if (line == null || line.trim().equalsIgnoreCase("exit") || line.trim().equalsIgnoreCase("quit")) {
                break;
            }
            
            if (line.trim().isEmpty()) {
                continue;
            }
            
            try {
                executeCommand(line);
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
        
        System.out.println("Goodbye!");
    }
    
    /**
     * Execute a SQL command
     */
    private void executeCommand(String sql) {
        System.out.println("Executing: " + sql);
        System.out.println("[This is a stub - full implementation requires parser and executor]");
    }
}
