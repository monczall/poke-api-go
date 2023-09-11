package com.pokeapigo.core.pokemon.util.factory.embedable;

import com.pokeapigo.core.pokemon.PokemonAvailability;

public class PokemonAvailabilityFactory {

    public static PokemonAvailability validPokemonAvailabilityAllAvailable() {
        return new PokemonAvailability(true, true, true, true, true );
    }

    public static PokemonAvailability validPokemonAvailabilityNoneAvailable() {
        return new PokemonAvailability(false, false, false, false, false );
    }
}
