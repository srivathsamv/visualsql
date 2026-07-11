package com.visualsql.backend.dto;

import java.util.List;

public record GraphResponse(
        List<TableNode> nodes,
        List<RelationshipEdge> edges
) {
}
