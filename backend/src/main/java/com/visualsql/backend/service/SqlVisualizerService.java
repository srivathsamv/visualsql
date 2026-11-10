package com.visualsql.backend.service;

import com.visualsql.backend.dto.GraphResponse;

/**
 * Interface that shows the business logic allowed through the service
 */
public interface SqlVisualizerService {

    GraphResponse visualize(String sql);
}
