package com.pokeapigo.core.pokemon.dto.response;

import com.pokeapigo.core.pokemon.PokemonAvailability;
import com.pokeapigo.core.pokemon.PokemonTypeDuo;
import com.pokeapigo.core.pokemon.util.enums.PokemonRarity;

public record PokemonResponse(
        Integer pokedexId,
        Integer generationId,
        String name,
        PokemonTypeDuo pokemonTypes,
        PokemonRarity rarity,
        PokemonAvailability availability,
        Boolean visible
) {}
