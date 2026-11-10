package com.visualsql.backend.exception;

/**
 * Custom error class definition for SQL parsing errors
 */
public class SqlParseException extends RuntimeException {
    public SqlParseException(String message) {
        super(message);
    }
}
