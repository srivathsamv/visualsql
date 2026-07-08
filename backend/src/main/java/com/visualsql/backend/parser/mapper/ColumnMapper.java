package com.visualsql.backend.parser.mapper;

import com.visualsql.backend.dto.ColumnDto;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import org.springframework.stereotype.Component;

@Component
public class ColumnMapper {

    public ColumnDto map(ColumnDefinition columnDefinition, boolean isPrimaryKey, boolean isForeignKey) {
        String columnName = columnDefinition.getColumnName();
        String dataType = columnDefinition.getColDataType().toString();

        return new ColumnDto(columnName, dataType, isPrimaryKey, isForeignKey);
    }
}
