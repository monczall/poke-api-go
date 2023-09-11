package com.pokeapigo.core.pokemon.util.factory;

import com.pokeapigo.core.pokemon.PokemonEntity;
import com.pokeapigo.core.pokemon.util.enums.PokemonRarity;
import com.pokeapigo.core.pokemon.util.factory.embedable.PokemonAvailabilityFactory;
import com.pokeapigo.core.pokemon.util.factory.embedable.PokemonTypeDuoFactory;

import static com.pokeapigo.core.pokemon.util.PokemonTestConstants.*;

public class PokemonEntityFactory {

    public static PokemonEntity validPokemonEntityBulbasaur() {
        return createValidPokemonEntity(POKEMON_ID_ONE, POKEMON_NAME_BULBASAUR, null);
    }

    public static PokemonEntity validPokemonEntityBulbasaurPartyHat() {
        return createValidPokemonEntity(POKEMON_ID_ONE, POKEMON_NAME_BULBASAUR, POKEMON_VARIANT_PARTY_HAT);
    }

    public static PokemonEntity validPokemonEntityIvysaur() {
        return createValidPokemonEntity(POKEMON_ID_TWO, POKEMON_NAME_VENUSAUR, null);
    }

    public static PokemonEntity validPokemonEntityVenusaur() {
        return createValidPokemonEntity(POKEMON_ID_THREE, POKEMON_NAME_IVYSAUR, null);
    }

    private static PokemonEntity createValidPokemonEntity(Integer pokedexId, String name, String variant) {
        return new PokemonEntity(
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
