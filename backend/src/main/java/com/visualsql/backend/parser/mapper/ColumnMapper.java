package com.visualsql.backend.parser.mapper;

import com.visualsql.backend.dto.ColumnDto;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import org.springframework.stereotype.Component;

@Component
public class ColumnMapper {

    public ColumnDto map(ColumnDefinition columnDefinition) {
        String columnName = columnDefinition.getColumnName();
        String dataType = columnDefinition.getColDataType().toString();

        return new ColumnDto(columnName, dataType, false, false);
    }
}
