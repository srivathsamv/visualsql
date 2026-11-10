package com.visualsql.backend.parser.model;

import com.visualsql.backend.dto.RelationshipEdge;
import com.visualsql.backend.dto.TableNode;

import java.util.List;

/**
 * Record class to store the final, clean table object for easier representation
 * @param node  Table object with its extracted metadata
 * @param edges the different relations of the columns in this table
 */
public record ExtractedTable(
        TableNode node,
        List<RelationshipEdge> edges
) {
}
