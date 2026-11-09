package com.visualsql.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SqlRequest(@NotBlank(message = "SQL cannot be empty") @Size(max = 500_000, message = "SQL exceeds maximum allowed size") String sql) {
}
