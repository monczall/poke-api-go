package com.pokeapigo.core.exception.dto;

import java.time.Instant;
import java.util.Map;

public record ValidationErrorDto(
        Instant timestamp,
        int statusCode,
        String statusText,
        Map<String, String> errors,
        String path
) {
}
