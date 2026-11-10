package com.visualsql.backend.exception;

import java.time.LocalDateTime;

/**
 * General error response record
 * @param timestamp time of the error
 * @param status HTTP status returned
 * @param error error name
 * @param message error message
 * @param path the api path 
 */
public record ErrorResponse(

        LocalDateTime timestamp,

        int status,

        String error,

        String message,

        String path

) {}