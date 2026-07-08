package com.visualsql.backend.service.impl;

import com.visualsql.backend.dto.GraphResponse;
import com.visualsql.backend.dto.RelationshipEdge;
import com.visualsql.backend.dto.TableNode;
import com.visualsql.backend.parser.extractor.CreateTableExtractor;
import com.visualsql.backend.parser.model.ExtractedTable;
import com.visualsql.backend.service.SqlVisualizerService;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SqlVisualizerServiceImpl implements SqlVisualizerService {

    private final CreateTableExtractor createTableExtractor;

    public SqlVisualizerServiceImpl(CreateTableExtractor createTableExtractor) {
        this.createTableExtractor = createTableExtractor;
    }

    @Override
    public GraphResponse visualize(String sql) {
        try {
            Statements statements = CCJSqlParserUtil.parseStatements(sql);

            List<TableNode> nodes = new ArrayList<>();
            List<RelationshipEdge> edges = new ArrayList<>();

            for(Statement statement: statements.getStatements()) {
                if(statement instanceof CreateTable) {
                    ExtractedTable table = createTableExtractor.extract((CreateTable) statement);
                    nodes.add(table.node());
                    edges.addAll(table.edges());
                }
            }

            return new GraphResponse(nodes, edges);
        } catch(Exception e) {
            throw new IllegalArgumentException("Failed to Parse SQL.");
        }
    }
}
