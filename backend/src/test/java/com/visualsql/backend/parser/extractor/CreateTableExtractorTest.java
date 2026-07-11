package com.visualsql.backend.parser.extractor;

import com.visualsql.backend.parser.mapper.ColumnMapper;
import com.visualsql.backend.parser.model.ExtractedTable;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateTableExtractorTest {

    private final CreateTableExtractor extractor =
            new CreateTableExtractor(
                    new ColumnMapper(),
                    new PrimaryKeyExtractor(),
                    new ForeignKeyExtractor(),
                    new RelationshipExtractor()
            );

    @Test
    public void testExtractTableWithForeignKey() throws Exception {

        String sql = """
                CREATE TABLE orders(
                    id BIGINT PRIMARY KEY,
                    user_id BIGINT,

                    FOREIGN KEY(user_id)
                    REFERENCES users(id)
                );
                """;

        CreateTable table = (CreateTable) CCJSqlParserUtil.parse(sql);

        ExtractedTable extracted = extractor.extract(table);

        assertNotNull(extracted);

        assertEquals("orders", extracted.node().tableName());
        assertEquals(2, extracted.node().columns().size());

        assertTrue(
                extracted.node()
                        .columns()
                        .stream()
                        .filter(column -> column.name().equals("id"))
                        .findFirst()
                        .orElseThrow()
                        .primaryKey()
        );

        assertTrue(
                extracted.node()
                        .columns()
                        .stream()
                        .filter(column -> column.name().equals("user_id"))
                        .findFirst()
                        .orElseThrow()
                        .foreignKey()
        );

        assertEquals(1, extracted.edges().size());
    }
}