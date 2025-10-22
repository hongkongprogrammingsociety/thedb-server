package org.hkprog.thedb.server;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

/**
 * TheDB TCP/IP Server
 */
public class TheDBServer {
    
    private final int port;
    private final String dataDirectory;
    private ServerSocket serverSocket;
    private ExecutorService threadPool;
    private volatile boolean running;
    
    public TheDBServer(int port, String dataDirectory) {
        this.port = port;
        this.dataDirectory = dataDirectory;
        this.threadPool = Executors.newFixedThreadPool(10);
    }
    
    /**
     * Start the database server
     */
    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        running = true;
        
        System.out.println("TheDB Server started on port " + port);
        System.out.println("Data directory: " + dataDirectory);
        System.out.println("Waiting for client connections...");
        
        while (running) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                threadPool.execute(new ClientHandler(clientSocket));
            } catch (IOException e) {
                if (running) {
                    System.err.println("Error accepting client connection: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Stop the server
     */
    public void stop() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing server socket: " + e.getMessage());
        }
        threadPool.shutdown();
    }
    
    /**
     * Client connection handler
     */
    private static class ClientHandler implements Runnable {
        private final Socket socket;
        
        public ClientHandler(Socket socket) {
            this.socket = socket;
        }
        
        @Override
        public void run() {
            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
            ) {
                out.println("TheDB Server v0.0.1");
                out.println("Type SQL commands or 'quit' to exit");
                
                String line;
                while ((line = in.readLine()) != null) {
                    if ("quit".equalsIgnoreCase(line.trim())) {
                        break;
                    }
                    
                    // Process SQL command
                    System.out.println("Received SQL: " + line);
                    out.println("OK - Query executed (stub)");
                }
            } catch (IOException e) {
                System.err.println("Client handler error: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    // Ignore
                }
            }
        }
    }
}
