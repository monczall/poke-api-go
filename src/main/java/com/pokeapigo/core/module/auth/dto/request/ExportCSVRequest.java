package com.pokeapigo.core.module.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request model for CSV Export")
public record ExportCSVRequest(
        @Schema(description = "Name of CSV file to be exported")
        String name
) {
}
