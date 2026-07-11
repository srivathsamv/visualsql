package com.visualsql.backend.parser.extractor;

import com.visualsql.backend.parser.model.ForeignKeyReference;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ForeignKeyExtractorTest {

    private final ForeignKeyExtractor extractor = new ForeignKeyExtractor();

    @Test
    public void testExtractForeignKey() throws Exception {

        String sql = """
                CREATE TABLE orders(
                    id BIGINT PRIMARY KEY,
                    user_id BIGINT,
                    
                    FOREIGN KEY(user_id)
                    REFERENCES users(id)
                );
                """;

        CreateTable table = (CreateTable) CCJSqlParserUtil.parse(sql);

        Map<String, ForeignKeyReference> foreignKeys = extractor.extract(table);

        assertEquals(1, foreignKeys.size());

        ForeignKeyReference foreignKey = foreignKeys.get("user_id");

        assertNotNull(foreignKey);

        assertEquals("users", foreignKey.referencedTable());
        assertEquals("id", foreignKey.referencedColumn());
    }
}
