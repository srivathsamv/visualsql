package com.visualsql.backend.parser.extractor;

import com.visualsql.backend.dto.ColumnDto;
import com.visualsql.backend.dto.RelationshipEdge;
import com.visualsql.backend.dto.TableNode;
import com.visualsql.backend.parser.mapper.ColumnMapper;
import com.visualsql.backend.parser.model.ExtractedTable;
import com.visualsql.backend.parser.model.ForeignKeyReference;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class CreateTableExtractor {

    private final ColumnMapper columnMapper;
    private final PrimaryKeyExtractor primaryKeyExtractor;
    private final ForeignKeyExtractor foreignKeyExtractor;
    private final RelationshipExtractor relationshipExtractor;

    public CreateTableExtractor(
            ColumnMapper columnMapper,
            PrimaryKeyExtractor primaryKeyExtractor,
            ForeignKeyExtractor foreignKeyExtractor,
            RelationshipExtractor relationshipExtractor
    ) {
        this.columnMapper = columnMapper;
        this.primaryKeyExtractor = primaryKeyExtractor;
        this.foreignKeyExtractor = foreignKeyExtractor;
        this.relationshipExtractor = relationshipExtractor;
    }

    public ExtractedTable extract(CreateTable createTable) {
        String tableName = createTable.getTable().getName();
        Set<String> primaryKeys = primaryKeyExtractor.extract(createTable);
        Map<String, ForeignKeyReference> foreignKeys =  foreignKeyExtractor.extract(createTable);
        List<RelationshipEdge> edges =
                relationshipExtractor.extract(
                        tableName,
                        foreignKeys
                );
        List<ColumnDto> columns = createTable.getColumnDefinitions().stream().map(column -> columnMapper.map(column, primaryKeys.contains(column.getColumnName()), foreignKeys.containsKey(column.getColumnName()))).toList();
        return new ExtractedTable(new TableNode(normalizeId(tableName), tableName, columns), edges);
    }

    private String normalizeId(String tableName) {
        return tableName.toLowerCase();
    }
}
