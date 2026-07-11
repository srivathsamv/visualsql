package com.visualsql.backend.controller;

import com.visualsql.backend.dto.ColumnDto;
import com.visualsql.backend.dto.GraphResponse;
import com.visualsql.backend.dto.RelationshipEdge;
import com.visualsql.backend.dto.TableNode;
import com.visualsql.backend.service.SqlVisualizerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SqlParsingController.class)
class SqlParsingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SqlVisualizerService sqlVisualizerService;

    @Test
    void testGraphResponse() throws Exception {

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

        GraphResponse response = new GraphResponse(
                List.of(node),
                List.of(edge)
        );

        when(sqlVisualizerService.visualize(anyString()))
                .thenReturn(response);

        String requestBody = """
        {
            "sql": "CREATE TABLE users (id BIGINT PRIMARY KEY);"
        }
        """;

        mockMvc.perform(post("/api/v1/visualize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nodes").isArray())
                .andExpect(jsonPath("$.edges").isArray())
                .andExpect(jsonPath("$.nodes[0].tableName").value("users"))
                .andExpect(jsonPath("$.nodes[0].columns[0].name").value("id"))
                .andExpect(jsonPath("$.edges[0].id").value("orders-users"))
                .andExpect(jsonPath("$.edges[0].source").value("orders"))
                .andExpect(jsonPath("$.edges[0].target").value("users"))
                .andExpect(jsonPath("$.edges[0].label").value("user_id"));
    }
}