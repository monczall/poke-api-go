package com.pokeapigo.core.module.pokemon.dto.response;

import com.pokeapigo.core.module.pokemon.PokemonAvailability;
import com.pokeapigo.core.module.pokemon.PokemonTypeDuo;
import com.pokeapigo.core.module.pokemon.util.enums.PokemonRarity;

import java.util.UUID;

public record PokemonResponse(
        UUID pokemonId,
        Integer pokedexId,
        Integer generationId,
        String name,
        String variant,
        PokemonTypeDuo pokemonTypes,
        PokemonRarity rarity,
        PokemonAvailability availability,
        Boolean visible
) {
}
