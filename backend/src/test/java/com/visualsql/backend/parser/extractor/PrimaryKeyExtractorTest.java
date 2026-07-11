package com.visualsql.backend.parser.extractor;

import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrimaryKeyExtractorTest {

    private final PrimaryKeyExtractor extractor = new PrimaryKeyExtractor();

    @Test
    public void testExtractPrimaryKeyInline() throws Exception {
        String sql = """
                CREATE TABLE users(
                    id BIGINT PRIMARY KEY,
                    username VARCHAR(55)
                );
                """;

        CreateTable table = (CreateTable) CCJSqlParserUtil.parse(sql);

        Set<String> primaryKeys = extractor.extract(table);

        assertEquals(1, primaryKeys.size());
        assertTrue(primaryKeys.contains("id"));
    }

    @Test
    public void testExtractPrimaryKeyComposite() throws Exception {

        String sql = """
                CREATE TABLE enrollments(
                    student_id BIGINT,
                    course_id BIGINT,
                    
                    PRIMARY KEY(student_id, course_id)
                );
                """;

        CreateTable table = (CreateTable) CCJSqlParserUtil.parse(sql);

        Set<String> primaryKeys = extractor.extract(table);

        assertEquals(2, primaryKeys.size());
        assertTrue(primaryKeys.contains("student_id"));
        assertTrue(primaryKeys.contains("course_id"));
    }
}
