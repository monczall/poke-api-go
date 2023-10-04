package com.pokeapigo.core.module.pokemon.dto.request;

import jakarta.validation.constraints.NotNull;

public record PokemonVisibilityRequest(
        @NotNull(message = "{pokemon.visibility.notNull}")
        Boolean visible
) {
}
