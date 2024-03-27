package com.pokeapigo.core.module.pokemon.dto.response;

import com.pokeapigo.core.module.pokemon.PokemonAvailability;
import com.pokeapigo.core.module.pokemon.PokemonTypeDuo;
import com.pokeapigo.core.module.pokemon.util.enums.PokemonRarity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record PokemonResponse(
        @Schema(description = "UUID of Pokemon")
        UUID pokemonId,

        @Schema(description = "Pokedex ID of Pokemon")
        Integer pokedexId,

        @Schema(description = "Generation ID of Pokemon")
        Integer generationId,

        @Schema(description = "Name of Pokemon")
        String name,

        @Schema(description = "Variant of Pokemon, ex. 'Party Hat', 'Alola', etc.")
        String variant,

        @Schema(description = "Object containing both types of Pokemon")
        PokemonTypeDuo pokemonTypes,

        @Schema(description = "Rarity of Pokemon, ex. 'Normal', 'Legendary', 'Mythic'")
        PokemonRarity rarity,

        @Schema(description = "Object containing different Pokemon available flags")
        PokemonAvailability availability,

        @Schema(description = "Flag describing if newly created Pokemon is valid or not")
        Boolean visible
) {
}
