package com.pokeapigo.core.pokemon.mapper;

import com.pokeapigo.core.pokemon.Pokemon;
import com.pokeapigo.core.pokemon.dto.request.PokemonRequest;
import com.pokeapigo.core.pokemon.dto.response.PokemonResponse;

public class PokemonMapper {

    public static Pokemon toEntity(PokemonRequest pokemonRequest) {
        return new Pokemon(
                pokemonRequest.pokedexId(),
                pokemonRequest.generationId(),
                pokemonRequest.name(),
                pokemonRequest.variant(),
                pokemonRequest.pokemonTypes(),
                pokemonRequest.rarity(),
                pokemonRequest.availability()
        );
    }

    public static PokemonResponse toPokemonResponse(Pokemon pokemon) {
        return new PokemonResponse(
                pokemon.getPokedexId(),
                pokemon.getGenerationId(),
                pokemon.getName(),
                pokemon.getPokemonTypes(),
                pokemon.getRarity(),
                pokemon.getAvailability(),
                pokemon.getVisible()
        );
    }
}
