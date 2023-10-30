package com.pokeapigo.core.module.pokemon.dto.response;

public record PokemonDeleteResponse(
        Integer pokedexId,
        String name,
        String variant,
        String message
) {
}
