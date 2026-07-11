package com.visualsql.backend.parser.extractor;

import com.visualsql.backend.parser.model.ForeignKeyReference;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.table.ForeignKeyIndex;
import net.sf.jsqlparser.statement.create.table.Index;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ForeignKeyExtractor {

    public Map<String, ForeignKeyReference> extract(CreateTable createTable) {

        Map<String, ForeignKeyReference> foreignKeyReferences = new HashMap<>();

        List<Index> indexes = createTable.getIndexes();

        if (indexes == null || indexes.isEmpty()) {
            return foreignKeyReferences;
        }

        for (Index index : indexes) {

            if (!(index instanceof ForeignKeyIndex foreignKey)) {
                continue;
            }

            String sourceColumn = foreignKey.getColumns().getFirst().getColumnName();
            String referencedTable = foreignKey.getTable().getName();
            String referencedColumn = foreignKey.getReferencedColumnNames().getFirst();

            foreignKeyReferences.put(
                    sourceColumn,
                    new ForeignKeyReference(
                            sourceColumn,
                            referencedTable,
                            referencedColumn
                    )
            );
        }
        return foreignKeyReferences;
    }
}