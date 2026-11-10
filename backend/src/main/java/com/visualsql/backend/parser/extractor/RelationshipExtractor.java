package com.visualsql.backend.parser.extractor;

import com.visualsql.backend.dto.RelationshipEdge;
import com.visualsql.backend.parser.model.ForeignKeyReference;
import com.visualsql.backend.util.TableID;

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
            String source = TableID.normalize(sourceTable);
            String target = TableID.normalize(reference.referencedTable());
            String sourceHandle = source + "-" + TableID.normalize(reference.sourceColumn());
            String targetHandle = target + "-" + TableID.normalize(reference.referencedColumn());
            edges.add(new RelationshipEdge(
              sourceHandle + "->" + targetHandle,
              source,
              sourceHandle,
              target,
              targetHandle,
              reference.sourceColumn()
            ));
        }
        return edges;
    }
}
