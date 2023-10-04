package com.pokeapigo.core.module.pokemon.util.factory.embedable;

import com.pokeapigo.core.module.pokemon.PokemonAvailability;

public class PokemonAvailabilityFactory {

    public static PokemonAvailability validPokemonAvailabilityAllAvailable() {
        return new PokemonAvailability(true, true, true, true, true, true, true, true, true, true, true);
    }
}
