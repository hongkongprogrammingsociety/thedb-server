package org.hkprog.thedb.storage;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Storage Engine - manages persistent data storage
 */
public class StorageEngine {
    
    private final String dataDirectory;
    private final Map<String, Table> tables;
    
    public StorageEngine(String dataDirectory) {
        this.dataDirectory = dataDirectory;
        this.tables = new HashMap<>();
        initializeDataDirectory();
    }
    
    private void initializeDataDirectory() {
        try {
            Files.createDirectories(Paths.get(dataDirectory));
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize data directory", e);
        }
    }
    
    /**
     * Create a new table
     */
    public void createTable(String tableName, TableSchema schema) {
        if (tables.containsKey(tableName)) {
            throw new RuntimeException("Table already exists: " + tableName);
        }
        Table table = new Table(tableName, schema);
        tables.put(tableName, table);
        System.out.println("Created table: " + tableName);
    }
    
    /**
     * Drop a table
     */
    public void dropTable(String tableName) {
        tables.remove(tableName);
        System.out.println("Dropped table: " + tableName);
    }
    
    /**
     * Get a table
     */
    public Table getTable(String tableName) {
        return tables.get(tableName);
    }
    
    /**
     * Table representation
     */
    public static class Table {
        private final String name;
        private final TableSchema schema;
        private final List<Row> rows;
        
        public Table(String name, TableSchema schema) {
            this.name = name;
            this.schema = schema;
            this.rows = new ArrayList<>();
        }
        
        public String getName() { return name; }
        public TableSchema getSchema() { return schema; }
        public List<Row> getRows() { return rows; }
        
        public void insertRow(Row row) {
            rows.add(row);
        }
    }
    
    /**
     * Table schema
     */
    public static class TableSchema {
        private final List<Column> columns;
        
        public TableSchema(List<Column> columns) {
            this.columns = columns;
        }
        
        public List<Column> getColumns() { return columns; }
        
        public static class Column {
            private final String name;
            private final String type;
            private final boolean nullable;
            
            public Column(String name, String type, boolean nullable) {
                this.name = name;
                this.type = type;
                this.nullable = nullable;
            }
            
            public String getName() { return name; }
            public String getType() { return type; }
            public boolean isNullable() { return nullable; }
        }
    }
    
    /**
     * Row of data
     */
    public static class Row {
        private final Map<String, Object> values;
        
        public Row(Map<String, Object> values) {
            this.values = values;
        }
        
        public Object getValue(String column) {
            return values.get(column);
        }
        
        public Map<String, Object> getValues() {
            return values;
        }
    }
}
