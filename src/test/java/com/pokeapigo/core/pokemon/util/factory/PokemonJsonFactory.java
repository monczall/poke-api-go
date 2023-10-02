package com.pokeapigo.core.pokemon.util.factory;

public class PokemonJsonFactory {

    public static String CREATE_POKEMON_MINIMAL = """
            {
                "pokedexId": 1,
                "generationId": 1,
                "name": "Bulbasaur",
                "pokemonTypes": {
                    "typeOne": "GRASS",
                    "typeTwo": "POISON"
                },
                "rarity": "STANDARD"
            }
            """;

    public static String CREATE_POKEMON_MINIMAL_VARIANT = """
            {
                "pokedexId": 1,
                "generationId": 1,
                "name": "Bulbasaur",
                "variant": "Party Hat",
                "pokemonTypes": {
                    "typeOne": "GRASS",
                    "typeTwo": "POISON"
                },
                "rarity": "STANDARD"
            }
            """;

    public static String CREATE_POKEMON_FULL = """
            {
                "pokedexId": 1,
                "generationId": 1,
                "name": "Bulbasaur",
                "variant": null,
                "pokemonTypes": {
                    "typeOne": "GRASS",
                    "typeTwo": "POISON"
                },
                "rarity": "STANDARD",
                "availability": {
                    "available": true,
                    "shiny": true,
                    "mega": true,
                    "megaFamily": true,
                    "shadow": true,
                    "tradeEvolve": true,
                    "tradeEvolveFamily": true,
                    "tradeable": true,
                    "raidable": true,
                    "alternateForm": true,
                    "costumeForm": true
                }
            }
            """;

    public static String CREATE_POKEMON_FULL_VARIANT = """
            {
                "pokedexId": 1,
                "generationId": 1,
                "name": "Bulbasaur",
                "variant": "Party Hat",
                "pokemonTypes": {
                    "typeOne": "GRASS",
                    "typeTwo": "POISON"
                },
                "rarity": "STANDARD",
                "availability": {
                    "available": true,
                    "shiny": true,
                    "mega": true,
                    "megaFamily": true,
                    "shadow": true,
                    "tradeEvolve": true,
                    "tradeEvolveFamily": true,
                    "tradeable": true,
                    "raidable": true,
                    "alternateForm": true,
                    "costumeForm": true
                }
            }
            """;

    public static String CHANGE_VISIBILITY_TRUE = """
            {
                "visible": true
            }
            """;

    public static String CHANGE_VISIBILITY_FALSE = """
            {
                "visible": false
            }
            """;

    public static String UPDATE_POKEMON_NO_VARIANT_IDENTICAL = """
            {
                "pokedexId": 1,
                "generationId": 1,
                "name": "Bulbasaur",
                "pokemonTypes": {
                    "typeOne": "GRASS",
                    "typeTwo": "POISON"
                },
                "rarity": "STANDARD"
            }
            """;

    public static String UPDATE_POKEMON_VARIANT_IDENTICAL = """
            {
                "pokedexId": 1,
                "generationId": 1,
                "name": "Bulbasaur",
                "variant": "Party Hat",
                "pokemonTypes": {
                    "typeOne": "GRASS",
                    "typeTwo": "POISON"
                },
                "rarity": "STANDARD"
            }
            """;

    public static String UPDATE_POKEMON_NO_VARIANT_CHANGED = """
            {
                "pokedexId": 1,
                "generationId": 1,
                "name": "Bulbasaur New",
                "pokemonTypes": {
                    "typeOne": "GRASS",
                    "typeTwo": "POISON"
                },
                "rarity": "STANDARD"
            }
            """;

    public static String UPDATE_POKEMON_VARIANT_CHANGED = """
            {
                "pokedexId": 1,
                "generationId": 1,
                "name": "Bulbasaur",
                "variant": "Party Hat New",
                "pokemonTypes": {
                    "typeOne": "GRASS",
                    "typeTwo": "POISON"
                },
                "rarity": "STANDARD"
            }
            """;

    public static String UPDATE_POKEMON_NAME_AND_VARIANT_CHANGED = """
            {
                "pokedexId": 1,
                "generationId": 1,
                "name": "Bulbasaur New",
                "variant": "Party Hat New",
                "pokemonTypes": {
                    "typeOne": "GRASS",
                    "typeTwo": "POISON"
                },
                "rarity": "STANDARD"
            }
            """;

    public static String UPDATE_POKEMON_NO_VARIANT_DUPLICATE = """
            {
                "pokedexId": 1,
                "generationId": 1,
                "name": "Bulbasaur",
                "pokemonTypes": {
                    "typeOne": "GRASS",
                    "typeTwo": "POISON"
                },
                "rarity": "STANDARD"
            }
            """;

    public static String UPDATE_SECOND_POKEMON_NO_VARIANT_DUPLICATE = """
            {
                "pokedexId": 2,
                "generationId": 1,
                "name": "Ivysaur",
                "pokemonTypes": {
                    "typeOne": "GRASS",
                    "typeTwo": "POISON"
                },
                "rarity": "STANDARD"
            }
            """;

    public static String UPDATE_POKEMON_VARIANT_DUPLICATE = """
            {
                "pokedexId": 1,
                "generationId": 1,
                "name": "Bulbasaur",
                "variant": "Party Hat",
                "pokemonTypes": {
                    "typeOne": "GRASS",
                    "typeTwo": "POISON"
                },
                "rarity": "STANDARD"
            }
            """;
}
