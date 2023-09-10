package com.pokeapigo.core.pokemon;

import com.pokeapigo.core.pokemon.dto.request.PokemonRequest;
import com.pokeapigo.core.pokemon.dto.response.PokemonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PokemonService {
    PokemonResponse createPokemon(PokemonRequest pokemonRequest);

    List<PokemonResponse> getAllPokemons();

    Page<PokemonResponse> getPagedPokemons(Pageable pageable, String name);
}
