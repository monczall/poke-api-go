package com.pokeapigo.core.pokemon.dto.request;

import jakarta.validation.constraints.NotNull;

public record PokemonVisibilityRequest(
        @NotNull Boolean visible
) {
}
