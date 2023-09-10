package com.pokeapigo.core.pokemon.mapper;

import com.pokeapigo.core.pokemon.Pokemon;
import com.pokeapigo.core.pokemon.dto.request.PokemonRequest;
import com.pokeapigo.core.pokemon.dto.response.PokemonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

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

    public static Page<PokemonResponse> toPagedPokemonResponse(Page<Pokemon> pagedPokemons) {
        return new PageImpl<>(
                pagedPokemons.getContent().stream()
                        .map(pokemon ->
                                new PokemonResponse(
                                        pokemon.getPokedexId(),
                                        pokemon.getGenerationId(),
                                        pokemon.getName(),
                                        pokemon.getPokemonTypes(),
                                        pokemon.getRarity(),
                                        pokemon.getAvailability(),
                                        pokemon.getVisible())
                        ).toList())
                ;
    }
}
