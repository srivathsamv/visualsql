package com.visualsql.backend.parser.extractor;

import com.visualsql.backend.dto.ColumnDto;
import com.visualsql.backend.dto.TableNode;
import com.visualsql.backend.parser.mapper.ColumnMapper;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreateTableExtractor {

    private final ColumnMapper columnMapper;

    public CreateTableExtractor(ColumnMapper columnMapper) {
        this.columnMapper = columnMapper;
    }

    public TableNode extract(CreateTable createTable) {
        String tableName = createTable.getTable().getName();

        List<ColumnDto> columns = createTable.getColumnDefinitions().stream().map(columnMapper::map).toList();

        return new TableNode(normalizeId(tableName), tableName, columns);
    }

    private String normalizeId(String tableName) {
        return tableName.toLowerCase();
    }
}
