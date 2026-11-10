package com.visualsql.backend.dto;

/**
 * record class that stores information regarding relation between two columns of different tables
 * @param id unique id assigned to the relation
 * @param source table that holds the foreign key
 * @param sourceHandle column that is labelled as the foreign key
 * @param target table that holds the primary key
 * @param targetHandle column labelled as the primary key
 * @param label name of the foreign key
 */
public record RelationshipEdge(
        String id,
        String source,
        String sourceHandle,
        String target,
        String targetHandle,
        String label
) {
}
