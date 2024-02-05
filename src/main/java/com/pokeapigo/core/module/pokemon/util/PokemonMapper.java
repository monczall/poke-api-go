package com.pokeapigo.core.module.pokemon.util;

import com.pokeapigo.core.module.pokemon.PokemonEntity;
import com.pokeapigo.core.module.pokemon.dto.request.PokemonRequest;
import com.pokeapigo.core.module.pokemon.dto.response.PokemonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Optional;

public final class PokemonMapper {

    public static PokemonEntity toEntity(PokemonRequest pokemonRequest) {
        return Optional.ofNullable(pokemonRequest).map(request -> new PokemonEntity(
                        request.pokedexId(),
                        request.generationId(),
                        request.name(),
                        request.variant(),
                        request.pokemonTypes(),
                        request.rarity(),
                        request.availability()
                )
        ).orElse(null);
    }

    public static PokemonResponse toPokemonResponse(PokemonEntity pokemonEntity) {
        return Optional.ofNullable(pokemonEntity).map(pokemon -> new PokemonResponse(
                        pokemon.getId(),
                        pokemon.getPokedexId(),
                        pokemon.getGenerationId(),
                        pokemon.getName(),
                        pokemon.getVariant(),
                        pokemon.getPokemonTypes(),
                        pokemon.getRarity(),
                        pokemon.getAvailability(),
                        pokemon.getVisible()
                )
        ).orElse(null);
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
