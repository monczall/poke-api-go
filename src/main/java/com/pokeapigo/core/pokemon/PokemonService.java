package com.pokeapigo.core.pokemon;

import com.pokeapigo.core.pokemon.dto.request.PokemonRequest;
import com.pokeapigo.core.pokemon.dto.response.PokemonResponse;

public interface PokemonService {
    PokemonResponse createPokemon(PokemonRequest pokemonRequest);
}
