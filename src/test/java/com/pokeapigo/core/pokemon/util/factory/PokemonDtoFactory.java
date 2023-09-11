package com.pokeapigo.core.pokemon.util.factory;

import com.pokeapigo.core.pokemon.dto.request.PokemonRequest;
import com.pokeapigo.core.pokemon.util.enums.PokemonRarity;
import com.pokeapigo.core.pokemon.util.factory.embedable.PokemonAvailabilityFactory;
import com.pokeapigo.core.pokemon.util.factory.embedable.PokemonTypeDuoFactory;

import static com.pokeapigo.core.pokemon.util.PokemonTestConstants.*;

public class PokemonDtoFactory {

    public static PokemonRequest validPokemonRequestBulbasaur() {
        return createValidPokemonRequest(POKEMON_ID_ONE, POKEMON_NAME_BULBASAUR, null);
    }

    public static PokemonRequest validPokemonRequestBulbasaurPartyHat() {
        return createValidPokemonRequest(POKEMON_ID_ONE, POKEMON_NAME_BULBASAUR, POKEMON_VARIANT_PARTY_HAT);
    }

    public static PokemonRequest validPokemonRequestVenusaur() {
        return createValidPokemonRequest(POKEMON_ID_TWO, POKEMON_NAME_VENUSAUR, null);
    }

    public static PokemonRequest validPokemonRequestIvysaur() {
        return createValidPokemonRequest(POKEMON_ID_THREE, POKEMON_NAME_IVYSAUR, null);
    }

    private static PokemonRequest createValidPokemonRequest(Integer pokedexId, String name, String variant) {
        return new PokemonRequest(
                pokedexId,
                GENERATION_ID_ONE,
                name,
                variant,
                PokemonTypeDuoFactory.validPokemonTypeDuoPoisonGrass(),
                PokemonRarity.STANDARD,
                PokemonAvailabilityFactory.validPokemonAvailabilityAllAvailable()
        );
    }
 }
