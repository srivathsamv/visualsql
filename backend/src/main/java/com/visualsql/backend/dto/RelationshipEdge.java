package com.visualsql.backend.dto;

public record RelationshipEdge(
        String id,
        String source,
        String target,
        String label
) {
}
