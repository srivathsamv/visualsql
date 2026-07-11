package com.visualsql.backend.service;

import com.visualsql.backend.dto.ColumnDto;
import com.visualsql.backend.dto.GraphResponse;
import com.visualsql.backend.dto.RelationshipEdge;
import com.visualsql.backend.dto.TableNode;
import com.visualsql.backend.parser.extractor.CreateTableExtractor;
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
                                false
                        )
                )
        );

        RelationshipEdge edge = new RelationshipEdge(
                "orders-users",
                "orders",
                "users",
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
}