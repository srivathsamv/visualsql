package com.visualsql.backend.dto;

import java.util.List;

/**
 * Record class to maintain the final state of the input statements
 * @param nodes list of data objects to represent each table in the SQL code
 * @param edges list of all foreign keys in the tables across the schema
 */
public record GraphResponse(
        List<TableNode> nodes,
        List<RelationshipEdge> edges
) {
}
