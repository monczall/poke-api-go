package com.pokeapigo.core.module.pokemon.util;

import com.pokeapigo.core.module.pokemon.PokemonEntity;
import com.pokeapigo.core.module.pokemon.dto.request.PokemonRequest;
import com.pokeapigo.core.module.pokemon.dto.response.PokemonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public final class PokemonMapper {

    public static PokemonEntity toEntity(PokemonRequest pokemonRequest) {
        return new PokemonEntity(
                pokemonRequest.pokedexId(),
                pokemonRequest.generationId(),
                pokemonRequest.name(),
                pokemonRequest.variant(),
                pokemonRequest.pokemonTypes(),
                pokemonRequest.rarity(),
                pokemonRequest.availability()
        );
    }

    public static PokemonResponse toPokemonResponse(PokemonEntity pokemon) {
        return new PokemonResponse(
                pokemon.getId(),
                pokemon.getPokedexId(),
                pokemon.getGenerationId(),
                pokemon.getName(),
                pokemon.getVariant(),
                pokemon.getPokemonTypes(),
                pokemon.getRarity(),
                pokemon.getAvailability(),
                pokemon.getVisible()
        );
    }

    public static Page<PokemonResponse> toPagedPokemonResponse(Page<PokemonEntity> pagedPokemons) {
        return new PageImpl<>(
                pagedPokemons.getContent().stream().map(pokemon ->
                        new PokemonResponse(
                                pokemon.getId(),
                                pokemon.getPokedexId(),
                                pokemon.getGenerationId(),
                                pokemon.getName(),
                                pokemon.getVariant(),
                                pokemon.getPokemonTypes(),
                                pokemon.getRarity(),
                                pokemon.getAvailability(),
                                pokemon.getVisible())
                ).toList()
        );
    }

    private PokemonMapper() {
    }
}
