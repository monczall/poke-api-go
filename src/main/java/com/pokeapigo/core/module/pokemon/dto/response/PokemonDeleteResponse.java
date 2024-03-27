package com.pokeapigo.core.module.pokemon.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response model for deleted Pokemon")
public record PokemonDeleteResponse(
        @Schema(description = "Pokedex ID of deleted Pokemon")
        Integer pokedexId,

        @Schema(description = "Name of deleted Pokemon")
        String name,

        @Schema(description = "Variant name of deleted Pokemon")
        String variant,

        @Schema(description = "Message attached to delete response")
        String message
) {
}
