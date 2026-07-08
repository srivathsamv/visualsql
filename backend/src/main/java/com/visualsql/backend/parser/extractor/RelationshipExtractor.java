package com.visualsql.backend.parser.extractor;

import com.visualsql.backend.dto.RelationshipEdge;
import com.visualsql.backend.parser.model.ForeignKeyReference;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class RelationshipExtractor {
    public List<RelationshipEdge> extract(
            String sourceTable,
            Map<String, ForeignKeyReference> foreignKeys
    ) {

        List<RelationshipEdge> edges = new ArrayList<>();

        for (ForeignKeyReference reference : foreignKeys.values()) {

            edges.add(
                    new RelationshipEdge(
                            sourceTable,
                            reference.referencedTable(),
                            reference.sourceColumn(),
                            reference.referencedColumn()
                    )
            );
        }

        return edges;
    }
}
