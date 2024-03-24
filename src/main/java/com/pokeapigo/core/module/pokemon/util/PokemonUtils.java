package com.pokeapigo.core.module.pokemon.util;

import com.pokeapigo.core.module.pokemon.PokemonEntity;
import com.pokeapigo.core.module.pokemon.PokemonTypeDuo;
import com.pokeapigo.core.module.pokemon.dto.request.PokemonRequest;

public class PokemonUtils {

    /**
     * Applies data from PokemonRequest object onto PokemonEntity object and returns it.
     * PokemonRequest data usually comes from PATCH or PUT requests.
     *
     * @param pokemon object to store new and old data
     * @param request object with incoming changes
     * @return PokemonEntity object with applied changes
     */
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

    /**
     * Applies data from csv PokemonEntity object onto existing PokemonEntity object and returns it.
     * This method was initially created for Pokemon CSV import.
     *
     * @param basePokemon    object already stored in database
     * @param newPokemonData object with incoming changes ex. from CSV
     * @return PokemonEntity object with applied changes
     */
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
