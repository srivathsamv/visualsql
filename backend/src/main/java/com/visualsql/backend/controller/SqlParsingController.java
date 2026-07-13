package com.visualsql.backend.controller;

import com.visualsql.backend.dto.GraphResponse;
import com.visualsql.backend.dto.SqlRequest;
import com.visualsql.backend.service.SqlVisualizerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class SqlParsingController {

    private final SqlVisualizerService sqlVisualizerService;

    public SqlParsingController(SqlVisualizerService sqlVisualizerService) {
        this.sqlVisualizerService = sqlVisualizerService;
    }

    @PostMapping("/visualize")
    public GraphResponse visualize(@Valid @RequestBody SqlRequest request) {
        return sqlVisualizerService.visualize(request.sql());
    }
}
