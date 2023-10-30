package com.pokeapigo.core.module.pokemon;

import com.pokeapigo.core.module.pokemon.dto.request.PokemonRequest;
import com.pokeapigo.core.module.pokemon.dto.request.PokemonVisibilityRequest;
import com.pokeapigo.core.module.pokemon.dto.response.PokemonDeleteResponse;
import com.pokeapigo.core.module.pokemon.dto.response.PokemonResponse;
import com.pokeapigo.core.module.pokemon.util.enums.PokemonType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public interface PokemonService {
    PokemonResponse createPokemon(PokemonRequest pokemonRequest, Locale locale);

    List<PokemonResponse> getAllPokemons();

    Page<PokemonResponse> getPagedPokemons(Pageable pageable, String search, Integer genId, PokemonType typeOne,
                                           PokemonType typeTwo, Locale locale);

    PokemonResponse updatePokemonData(UUID pokemonUUID, PokemonRequest request, Locale locale);

    PokemonResponse changePokemonVisibility(UUID pokemonUUID, PokemonVisibilityRequest request, Locale locale);

    PokemonDeleteResponse deletePokemon(UUID pokemonUUID, Locale locale);
}
