package com.pokeapigo.core.module.pokemon.util.factory;

import com.pokeapigo.core.module.pokemon.PokemonEntity;
import com.pokeapigo.core.module.pokemon.util.PokemonTestConstants;
import com.pokeapigo.core.module.pokemon.util.enums.PokemonRarity;
import com.pokeapigo.core.module.pokemon.util.factory.embedable.PokemonAvailabilityFactory;
import com.pokeapigo.core.module.pokemon.util.factory.embedable.PokemonTypeDuoFactory;

public class PokemonEntityFactory {

    public static PokemonEntity validPokemonEntityBulbasaur() {
        return createValidPokemonEntity(PokemonTestConstants.POKEMON_ID_ONE, PokemonTestConstants.POKEMON_NAME_BULBASAUR, null);
    }

    public static PokemonEntity validPokemonEntityBulbasaurPartyHat() {
        return createValidPokemonEntity(PokemonTestConstants.POKEMON_ID_ONE, PokemonTestConstants.POKEMON_NAME_BULBASAUR, PokemonTestConstants.POKEMON_VARIANT_PARTY_HAT);
    }

    public static PokemonEntity validPokemonEntityIvysaur() {
        return createValidPokemonEntity(PokemonTestConstants.POKEMON_ID_TWO, PokemonTestConstants.POKEMON_NAME_VENUSAUR, null);
    }

    public static PokemonEntity validPokemonEntityVenusaur() {
        return createValidPokemonEntity(PokemonTestConstants.POKEMON_ID_THREE, PokemonTestConstants.POKEMON_NAME_IVYSAUR, null);
    }

    private static PokemonEntity createValidPokemonEntity(Integer pokedexId, String name, String variant) {
        return new PokemonEntity(
                pokedexId,
                PokemonTestConstants.GENERATION_ID_ONE,
                name,
                variant,
                PokemonTypeDuoFactory.validPokemonTypeDuoPoisonGrass(),
                PokemonRarity.STANDARD,
                PokemonAvailabilityFactory.validPokemonAvailabilityAllAvailable(),
                true
        );
    }

    private static PokemonEntity createInvalidPokemonEntity(Integer pokedexId, String name, String variant) {
        return new PokemonEntity(
                pokedexId,
                PokemonTestConstants.GENERATION_ID_ONE,
                name,
                variant,
                PokemonTypeDuoFactory.validPokemonTypeDuoPoisonGrass(),
                PokemonRarity.STANDARD,
                PokemonAvailabilityFactory.validPokemonAvailabilityAllAvailable(),
                false
        );
    }
}
