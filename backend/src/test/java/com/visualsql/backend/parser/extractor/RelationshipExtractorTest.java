package com.visualsql.backend.parser.extractor;

import com.visualsql.backend.dto.RelationshipEdge;
import com.visualsql.backend.parser.model.ForeignKeyReference;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RelationshipExtractorTest {

    private final RelationshipExtractor extractor = new RelationshipExtractor();

    @Test
    void testCreateRelationshipEdge() {

        Map<String, ForeignKeyReference> foreignKeys = Map.of(
                "user_id",
                new ForeignKeyReference(
                        "user_id",
                        "users",
                        "id"
                )
        );

        List<RelationshipEdge> edges =
                extractor.extract("orders", foreignKeys);

        assertEquals(1, edges.size());

        RelationshipEdge edge = edges.getFirst();

        assertEquals("orders", edge.source());
        assertEquals("orders-user_id", edge.sourceHandle());
        assertEquals("users", edge.target());
        assertEquals("users-id", edge.targetHandle());
        assertEquals("user_id", edge.label());
        assertEquals("orders-users", edge.id());
    }
}