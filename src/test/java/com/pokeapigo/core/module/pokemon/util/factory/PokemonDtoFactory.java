package com.pokeapigo.core.module.pokemon.util.factory;

import com.pokeapigo.core.module.pokemon.dto.request.PokemonRequest;
import com.pokeapigo.core.module.pokemon.util.PokemonTestConstants;
import com.pokeapigo.core.module.pokemon.util.enums.PokemonRarity;
import com.pokeapigo.core.module.pokemon.util.factory.embedable.PokemonAvailabilityFactory;
import com.pokeapigo.core.module.pokemon.util.factory.embedable.PokemonTypeDuoFactory;

public class PokemonDtoFactory {

    public static PokemonRequest validPokemonRequestBulbasaur() {
        return createValidPokemonRequest(PokemonTestConstants.POKEMON_ID_ONE, PokemonTestConstants.POKEMON_NAME_BULBASAUR, null);
    }

    public static PokemonRequest validPokemonRequestBulbasaurPartyHat() {
        return createValidPokemonRequest(PokemonTestConstants.POKEMON_ID_ONE, PokemonTestConstants.POKEMON_NAME_BULBASAUR, PokemonTestConstants.POKEMON_VARIANT_PARTY_HAT);
    }

    public static PokemonRequest validPokemonRequestVenusaur() {
        return createValidPokemonRequest(PokemonTestConstants.POKEMON_ID_TWO, PokemonTestConstants.POKEMON_NAME_VENUSAUR, null);
    }

    public static PokemonRequest validPokemonRequestIvysaur() {
        return createValidPokemonRequest(PokemonTestConstants.POKEMON_ID_THREE, PokemonTestConstants.POKEMON_NAME_IVYSAUR, null);
    }

    private static PokemonRequest createValidPokemonRequest(Integer pokedexId, String name, String variant) {
        return new PokemonRequest(
                pokedexId,
                PokemonTestConstants.GENERATION_ID_ONE,
                name,
                variant,
                PokemonTypeDuoFactory.validPokemonTypeDuoPoisonGrass(),
                PokemonRarity.STANDARD,
                PokemonAvailabilityFactory.validPokemonAvailabilityAllAvailable()
        );
    }
}
