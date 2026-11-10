package com.visualsql.backend.service.impl;

import com.visualsql.backend.dto.ColumnDto;
import com.visualsql.backend.dto.GraphResponse;
import com.visualsql.backend.dto.RelationshipEdge;
import com.visualsql.backend.dto.TableNode;
import com.visualsql.backend.exception.SqlParseException;
import com.visualsql.backend.parser.extractor.CreateTableExtractor;
import com.visualsql.backend.parser.model.ExtractedTable;
import com.visualsql.backend.service.SqlVisualizerService;
import com.visualsql.backend.util.TableID;

import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of the service interface (business logic of converting statements into a comprehendable diagram)
 * Current state: processes CREATE TABLE statements
 */
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

            for(Statement statement: statements) {
                if(statement instanceof CreateTable) {
                    ExtractedTable table = createTableExtractor.extract((CreateTable) statement);
                    nodes.add(table.node());
                    edges.addAll(table.edges());
                }
            }

            return new GraphResponse(markReferencedColumns(nodes, edges), edges);
        } catch(Exception e) {
            throw new SqlParseException(
                    "Unable to parse SQL: " + e.getMessage()
            );
        }
    }

    private List<TableNode> markReferencedColumns(List<TableNode> nodes, List<RelationshipEdge> edges){
        Set<String> referencedHandles = edges.stream().map(RelationshipEdge::targetHandle).collect(Collectors.toSet());

        return nodes.stream().map(node -> new TableNode(
            node.id(), node.tableName(), node.columns().stream().map(col -> new ColumnDto(
                col.name(), col.type(), col.primaryKey(), col.foreignKey(), referencedHandles.contains(node.id() + "-" + TableID.normalize(col.name())))).toList()
        )).toList();
    }
}
