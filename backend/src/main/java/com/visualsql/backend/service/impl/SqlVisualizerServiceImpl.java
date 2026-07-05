package com.visualsql.backend.service.impl;

import com.visualsql.backend.dto.GraphResponse;
import com.visualsql.backend.dto.TableNode;
import com.visualsql.backend.parser.extractor.CreateTableExtractor;
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

            for(Statement statement: statements.getStatements()) {
                if(statement instanceof CreateTable) {
                    nodes.add(createTableExtractor.extract((CreateTable) statement));
                }
            }

            return new GraphResponse(nodes, List.of());
        } catch(Exception e) {
            throw new IllegalArgumentException("Failed to Parse SQL.");
        }
    }
}
