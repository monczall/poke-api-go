package com.pokeapigo.core.module.pokemon.util;

import com.pokeapigo.core.module.pokemon.PokemonEntity;
import com.pokeapigo.core.module.pokemon.PokemonTypeDuo;
import com.pokeapigo.core.module.pokemon.dto.request.PokemonRequest;

public class PokemonUtils {

    public static PokemonEntity updatePokemonEntityData(PokemonEntity pokemon, PokemonRequest request) {
        pokemon.setPokedexId(request.pokedexId());
        pokemon.setGenerationId(request.generationId());
        pokemon.setName(request.name());
        pokemon.setVariant(request.variant());
        pokemon.setPokemonTypes(request.pokemonTypes());
        pokemon.setRarity(request.rarity());
        pokemon.setAvailability(request.availability());

        return pokemon;
    }

    public static PokemonEntity updatePokemonEntityData(PokemonEntity basePokemon, PokemonEntity newPokemonData) {
        basePokemon.setGenerationId(newPokemonData.getGenerationId());
        basePokemon.setPokemonTypes(new PokemonTypeDuo(
                newPokemonData.getPokemonTypes().getTypeOne(),
                newPokemonData.getPokemonTypes().getTypeTwo()
        ));
        basePokemon.setRarity(newPokemonData.getRarity());
        basePokemon.setAvailability(newPokemonData.getAvailability());

        return basePokemon;
    }

    private PokemonUtils() {
    }
}
