package com.visualsql.backend.service;

import com.visualsql.backend.dto.GraphResponse;

public interface SqlVisualizerService {

    GraphResponse visualize(String sql);
}
