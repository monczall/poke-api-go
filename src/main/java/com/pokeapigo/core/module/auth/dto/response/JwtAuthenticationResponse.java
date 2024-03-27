package com.pokeapigo.core.module.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Object containing JWT Auth response")
public record JwtAuthenticationResponse(
        @Schema(description = "Bearer Token")
        String bearerToken
) {
}
