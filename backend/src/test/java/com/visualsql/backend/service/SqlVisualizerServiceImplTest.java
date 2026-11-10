package com.visualsql.backend.service;

import com.visualsql.backend.dto.ColumnDto;
import com.visualsql.backend.dto.GraphResponse;
import com.visualsql.backend.dto.RelationshipEdge;
import com.visualsql.backend.dto.TableNode;
import com.visualsql.backend.parser.extractor.CreateTableExtractor;
import com.visualsql.backend.parser.extractor.ForeignKeyExtractor;
import com.visualsql.backend.parser.extractor.PrimaryKeyExtractor;
import com.visualsql.backend.parser.extractor.RelationshipExtractor;
import com.visualsql.backend.parser.mapper.ColumnMapper;
import com.visualsql.backend.parser.model.ExtractedTable;
import com.visualsql.backend.service.impl.SqlVisualizerServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SqlVisualizerServiceImplTest {

    @Test
    void testParsingForCreateTableExtractor() {

        CreateTableExtractor extractor = mock(CreateTableExtractor.class);

        TableNode node = new TableNode(
                "users",
                "users",
                List.of(
                        new ColumnDto(
                                "id",
                                "BIGINT",
                                true,
                                false,
                                false
                        )
                )
        );

        RelationshipEdge edge = new RelationshipEdge(
                "orders-users",
                "orders",
                "orders-user_id",
                "users",
                "user-id",
                "user_id"
        );

        ExtractedTable extractedTable = new ExtractedTable(
                node,
                List.of(edge)
        );

        when(extractor.extract(any()))
                .thenReturn(extractedTable);

        SqlVisualizerServiceImpl service =
                new SqlVisualizerServiceImpl(extractor);

        String sql = """
                CREATE TABLE users (
                    id BIGINT PRIMARY KEY
                );
                """;

        GraphResponse response = service.visualize(sql);

        assertNotNull(response);

        assertEquals(1, response.nodes().size());
        assertEquals(1, response.edges().size());

        TableNode returnedNode = response.nodes().getFirst();
        RelationshipEdge returnedEdge = response.edges().getFirst();

        assertEquals("users", returnedNode.tableName());
        assertEquals("users", returnedNode.id());

        assertEquals("orders", returnedEdge.source());
        assertEquals("users", returnedEdge.target());

        verify(extractor, times(1)).extract(any());
        verifyNoMoreInteractions(extractor);
    }

    @Test
    void edgeConnectsForMixedCaseTableNames() {
        SqlVisualizerServiceImpl service = new SqlVisualizerServiceImpl(
            new CreateTableExtractor(new ColumnMapper(), new PrimaryKeyExtractor(),
                    new ForeignKeyExtractor(), new RelationshipExtractor()));

        GraphResponse graph = service.visualize("""
            CREATE TABLE Users (id BIGINT PRIMARY KEY);
            CREATE TABLE Orders (
                id BIGINT PRIMARY KEY,
                user_id BIGINT,
                FOREIGN KEY (user_id) REFERENCES Users(id)
            );
            """);

        RelationshipEdge edge = graph.edges().getFirst();
        assertEquals("orders", edge.source());
        assertEquals("users", edge.target());
        assertEquals("orders-user_id", edge.sourceHandle());
        assertEquals("users-id", edge.targetHandle());
    }

    @Test
    void multipleForeignKeysToSameTableProduceUniqueEdges() {
        SqlVisualizerServiceImpl service = new SqlVisualizerServiceImpl(
            new CreateTableExtractor(new ColumnMapper(), new PrimaryKeyExtractor(),
                new ForeignKeyExtractor(), new RelationshipExtractor()));

        GraphResponse graph = service.visualize("""
            CREATE TABLE users (id BIGINT PRIMARY KEY);
            CREATE TABLE messages (
                id BIGINT PRIMARY KEY,
                sender_id BIGINT,
                recipient_id BIGINT,
                FOREIGN KEY (sender_id) REFERENCES users(id),
                FOREIGN KEY (recipient_id) REFERENCES users(id)
            );
            """);

        assertEquals(2, graph.edges().size());
        assertEquals(2, graph.edges().stream().map(RelationshipEdge::id).distinct().count());
    }

    @Test
    void marksColumnsReferencedByForeignKeys() {
        SqlVisualizerServiceImpl service = new SqlVisualizerServiceImpl(
            new CreateTableExtractor(new ColumnMapper(), new PrimaryKeyExtractor(),
                    new ForeignKeyExtractor(), new RelationshipExtractor()));

        GraphResponse graph = service.visualize("""
            CREATE TABLE users (id BIGINT PRIMARY KEY, email VARCHAR(100) UNIQUE);
            CREATE TABLE invites (
                id BIGINT PRIMARY KEY,
                invited_email VARCHAR(100),
                FOREIGN KEY (invited_email) REFERENCES users(email)
            );
            """);

        TableNode users = graph.nodes().stream()
            .filter(n -> n.id().equals("users")).findFirst().orElseThrow();

        assertTrue(users.columns().stream().filter(c -> c.name().equals("email")).findFirst().orElseThrow().referenced());
        assertFalse(users.columns().stream().filter(c -> c.name().equals("id")).findFirst().orElseThrow().referenced());
    }
}