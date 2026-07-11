package com.visualsql.backend.parser.mapper;

import com.visualsql.backend.dto.ColumnDto;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ColumnMapperTest {

    private final ColumnMapper mapper = new ColumnMapper();

    @Test
    public void testExtractColumnName() throws Exception {

        String sql = """
                CREATE TABLE users(
                    id BIGINT
                );
                """;

        CreateTable table = (CreateTable) CCJSqlParserUtil.parse(sql);

        ColumnDto dto = mapper.map(table.getColumnDefinitions().getFirst(),
                true,
                false);

        assertEquals("id", dto.name());
        assertEquals("BIGINT", dto.type());
        assertTrue(dto.primaryKey());
        assertFalse(dto.foreignKey());
    }
}
