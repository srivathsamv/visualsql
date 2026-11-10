package com.visualsql.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data object that contains information about the SQL code or statements that have to be visualized
 * @param sql - contains the SQL statements as strings, with max size of the request and error messages for input handling
 */
public record SqlRequest(@NotBlank(message = "SQL cannot be empty") @Size(max = 500_000, message = "SQL exceeds maximum allowed size") String sql) {
}
