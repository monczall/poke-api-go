package com.pokeapigo.core.module.pokemon.util;

public class PokemonConstants {

    public static final Integer DEX_ID_MIN = 1;
    public static final Integer DEX_ID_MAX = 1200;
    public static final Integer GEN_ID_MIN = 1;
    public static final Integer GEN_ID_MAX = 10;
    public static final Integer POK_NAME_MIN = 3;
    public static final Integer POK_NAME_MAX = 255;
    public static final Integer POKEMON_PAGE_MAX = 100;

    private PokemonConstants() {
        throw new UnsupportedOperationException("This is utility class and cannot be instantiated");
    }
}
