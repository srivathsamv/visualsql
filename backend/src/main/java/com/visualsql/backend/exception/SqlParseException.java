package com.visualsql.backend.exception;

public class SqlParseException extends RuntimeException {
    public SqlParseException(String message) {
        super(message);
    }
}
