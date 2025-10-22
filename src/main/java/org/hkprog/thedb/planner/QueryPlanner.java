package org.hkprog.thedb.planner;

import org.hkprog.thedb.ast.ASTNode;
import org.hkprog.thedb.executor.QueryExecutor.ExecutionPlan;

/**
 * Query Planner - optimizes queries and creates execution plans
 */
public class QueryPlanner {
    
    public QueryPlanner() {
        // Constructor
    }
    
    /**
     * Create an optimized execution plan from AST
     */
    public ExecutionPlan createPlan(ASTNode ast) {
        // Analyze AST
        // Choose indexes
        // Optimize joins
        // Create execution plan
        System.out.println("Creating query plan...");
        return new ExecutionPlan(ast);
    }
}
