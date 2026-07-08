package com.visualsql.backend.parser.model;

import com.visualsql.backend.dto.RelationshipEdge;
import com.visualsql.backend.dto.TableNode;

import java.util.List;

public record ExtractedTable(
        TableNode node,
        List<RelationshipEdge> edges
) {
}
