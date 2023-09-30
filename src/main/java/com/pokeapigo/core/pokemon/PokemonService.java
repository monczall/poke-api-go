package com.pokeapigo.core.pokemon;

import com.pokeapigo.core.pokemon.dto.request.PokemonRequest;
import com.pokeapigo.core.pokemon.dto.request.PokemonVisibilityRequest;
import com.pokeapigo.core.pokemon.dto.response.PokemonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public interface PokemonService {
    PokemonResponse createPokemon(PokemonRequest pokemonRequest, Locale locale);

    PokemonResponse updatePokemonData(UUID pokemonId, PokemonRequest request, Locale locale);

    PokemonResponse changePokemonVisibility(UUID pokemonId, PokemonVisibilityRequest request, Locale locale);

    List<PokemonResponse> getAllPokemons();

    Page<PokemonResponse> getPagedPokemons(Pageable pageable, String name, Locale locale);

}
