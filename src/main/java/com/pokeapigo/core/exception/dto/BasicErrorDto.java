package com.pokeapigo.core.exception.dto;

import java.time.Instant;

public record BasicErrorDto(
        Instant timestamp,
        int statusCode,
        String statusText,
        String message,
        String path) {
}
