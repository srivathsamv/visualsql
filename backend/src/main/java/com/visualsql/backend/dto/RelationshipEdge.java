package com.visualsql.backend.dto;

public record RelationshipEdge(
        String id,
        String source,
        String sourceHandle,
        String target,
        String targetHandle,
        String label
) {
}
