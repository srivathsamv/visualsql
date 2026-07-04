package com.visualsql.backend.service.impl;

import com.visualsql.backend.dto.ColumnDto;
import com.visualsql.backend.dto.GraphResponse;
import com.visualsql.backend.dto.RelationshipEdge;
import com.visualsql.backend.dto.TableNode;
import com.visualsql.backend.service.SqlVisualizerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SqlVisualizerServiceImpl implements SqlVisualizerService {

    @Override
    public GraphResponse visualize(String sql) {
        TableNode users = new TableNode(
                "users",
                "Users",
                List.of(
                        new ColumnDto("id", "INT", true, false),
                        new ColumnDto("name", "VARCHAR(255)", false, false),
                        new ColumnDto("email", "VARCHAR(255)", false, false)
                )
        );

        TableNode orders = new TableNode(
                "orders",
                "Orders",
                List.of(
                        new ColumnDto("id", "INT", true, false),
                        new ColumnDto("user_id", "INT", false, true),
                        new ColumnDto("amount", "DECIMAL(10,2)", false, false)
                )
        );

        RelationshipEdge relationship = new RelationshipEdge(
                "edge-1",
                "orders",
                "users",
                "FK: user_id → id"
        );

        return new GraphResponse(
                List.of(users, orders),
                List.of(relationship)
        );
    }
}
