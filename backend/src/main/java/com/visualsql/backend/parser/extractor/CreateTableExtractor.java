package com.visualsql.backend.parser.extractor;

import com.visualsql.backend.dto.ColumnDto;
import com.visualsql.backend.dto.TableNode;
import com.visualsql.backend.parser.mapper.ColumnMapper;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class CreateTableExtractor {

    private final ColumnMapper columnMapper;
    private final PrimaryKeyExtractor primaryKeyExtractor;

    public CreateTableExtractor(ColumnMapper columnMapper, PrimaryKeyExtractor primaryKeyExtractor) {
        this.columnMapper = columnMapper;
        this.primaryKeyExtractor = primaryKeyExtractor;
    }

    public TableNode extract(CreateTable createTable) {
        String tableName = createTable.getTable().getName();
        Set<String> primaryKeys = primaryKeyExtractor.extract(createTable);
        List<ColumnDto> columns = createTable.getColumnDefinitions().stream().map(column -> columnMapper.map(column, primaryKeys.contains(column.getColumnName()), false)).toList();
        return new TableNode(normalizeId(tableName), tableName, columns);
    }

    private String normalizeId(String tableName) {
        return tableName.toLowerCase();
    }
}
