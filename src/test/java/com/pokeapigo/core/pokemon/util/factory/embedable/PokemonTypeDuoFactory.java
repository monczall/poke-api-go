package com.pokeapigo.core.pokemon.util.factory.embedable;

import com.pokeapigo.core.pokemon.PokemonTypeDuo;
import com.pokeapigo.core.pokemon.util.enums.PokemonType;

public class PokemonTypeDuoFactory {
    public static PokemonTypeDuo validPokemonTypeDuoPoisonGrass() {
        return new PokemonTypeDuo(PokemonType.POISON, PokemonType.GRASS);
    }
}
