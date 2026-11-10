package com.visualsql.backend.controller;

import com.visualsql.backend.dto.GraphResponse;
import com.visualsql.backend.dto.SqlRequest;
import com.visualsql.backend.service.SqlVisualizerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest API controller with endpoints
 * SqlParsingController - has one endpoint - /api/v1/visualize that converts CREATE TABLE sql request to graph data
 */
@RestController
@RequestMapping("/api/v1")
public class SqlParsingController {

    private final SqlVisualizerService sqlVisualizerService;

    public SqlParsingController(SqlVisualizerService sqlVisualizerService) {
        this.sqlVisualizerService = sqlVisualizerService;
    }

    /**
     * Endpoint that processes a SQL statement and returns a relationship diagram
     * @param request - data object with SQL code or statements
     * @return a graph response containing table data and relationship edges with important metadata of the tables
     */
    @PostMapping("/visualize")
    public GraphResponse visualize(@Valid @RequestBody SqlRequest request) {
        return sqlVisualizerService.visualize(request.sql());
    }
}
