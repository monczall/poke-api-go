package com.pokeapigo.core.module.pokemon.util.factory.embedable;

import com.pokeapigo.core.module.pokemon.PokemonTypeDuo;
import com.pokeapigo.core.module.pokemon.util.enums.PokemonType;

public class PokemonTypeDuoFactory {
    public static PokemonTypeDuo validPokemonTypeDuoPoisonGrass() {
        return new PokemonTypeDuo(PokemonType.GRASS, PokemonType.POISON);
    }
}
