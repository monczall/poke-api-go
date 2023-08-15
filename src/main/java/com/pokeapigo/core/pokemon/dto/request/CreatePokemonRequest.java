package com.pokeapigo.core.pokemon.dto.request;

import com.pokeapigo.core.pokemon.PokemonAvailability;
import com.pokeapigo.core.pokemon.PokemonTypeDuo;
import com.pokeapigo.core.pokemon.util.enums.PokemonRarity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import static com.pokeapigo.core.pokemon.util.constants.PokemonErrorConstants.*;

public record CreatePokemonRequest(
        @NotNull(message = POKEMON_ID_RANGE_ERROR_MESSAGE)
        @Min(value = 1, message = POKEMON_ID_RANGE_ERROR_MESSAGE)
        @Max(value = 1200, message = POKEMON_ID_RANGE_ERROR_MESSAGE)
        Integer pokedexId,

        @NotNull(message = GENERATION_ID_RANGE_ERROR_MESSAGE)
        @Min(value = 1, message = GENERATION_ID_RANGE_ERROR_MESSAGE)
        @Max(value = 10, message = GENERATION_ID_RANGE_ERROR_MESSAGE)
        Integer generationId,

        @NotBlank(message = POKEMON_NAME_ERROR_MESSAGE)
        @Size(min = 3, max = 255, message = POKEMON_NAME_ERROR_MESSAGE)
        String name,

        @Valid
        @NotNull(message = POKEMON_TYPE_ERROR_MESSAGE)
        PokemonTypeDuo pokemonTypes,

        @NotNull(message = POKEMON_RARITY_ERROR_MESSAGE)
        PokemonRarity rarity,

        PokemonAvailability availability
) {
        public CreatePokemonRequest {
                if(availability == null) {
                        availability = new PokemonAvailability();
                }
        }
}
