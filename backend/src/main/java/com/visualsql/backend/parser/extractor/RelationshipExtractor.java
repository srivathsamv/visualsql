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
            edges.add(
                    new RelationshipEdge(
                            source + "-" + target,
                            source,
                            source + "-" + TableID.normalize(reference.sourceColumn()),
                            target,
                            target + "-" + TableID.normalize(reference.referencedColumn()),
                            TableID.normalize(reference.sourceColumn())
                    )
            );
        }

        return edges;
    }
}
