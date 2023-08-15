package com.pokeapigo.core.pokemon;

import com.pokeapigo.core.pokemon.dto.request.CreatePokemonRequest;

public interface PokemonService {
    Pokemon createPokemon(CreatePokemonRequest createPokemonRequest);
}
