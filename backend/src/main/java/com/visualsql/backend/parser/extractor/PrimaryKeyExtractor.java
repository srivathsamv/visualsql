package com.visualsql.backend.parser.extractor;

import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.table.Index;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class PrimaryKeyExtractor {

    public Set<String> extract(CreateTable createTable) {
        Set<String> primaryKeys = extractInlinePrimaryKeys(createTable);
        primaryKeys.addAll(extractTablePrimaryKeys(createTable));
        return primaryKeys;
    }

    private Set<String> extractInlinePrimaryKeys(CreateTable createTable) {
        Set<String> primaryKeys = new HashSet<>();
        for(ColumnDefinition columnDefinition : createTable.getColumnDefinitions()){
            List<String> specs = columnDefinition.getColumnSpecs();
            if (specs != null &&
                    specs.contains("PRIMARY") &&
                    specs.contains("KEY")) {

                primaryKeys.add(columnDefinition.getColumnName());
            }
        }
        return primaryKeys;
    }

    private Set<String> extractTablePrimaryKeys(CreateTable createTable) {
        Set<String> primaryKeys = new HashSet<>();
        if(createTable.getIndexes() == null)
            return primaryKeys;
        for (Index index : createTable.getIndexes()) {
            if ("PRIMARY KEY".equals(index.getType())) {

                index.getColumns()
                        .forEach(column -> primaryKeys.add(column.getColumnName()));
            }
        }
        return primaryKeys;
    }
}
