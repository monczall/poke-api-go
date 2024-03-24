package com.pokeapigo.core.module.pokemon.dto.request;

import com.pokeapigo.core.module.pokemon.PokemonAvailability;
import com.pokeapigo.core.module.pokemon.PokemonTypeDuo;
import com.pokeapigo.core.module.pokemon.util.enums.PokemonRarity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record PokemonRequest(
        @NotNull(message = "{pokemon.dexId.notnull}")
        @Min(value = 1, message = "{pokemon.dexId.rangeMin}")
        @Max(value = 1200, message = "{pokemon.dexId.rangeMax}")
        Integer pokedexId,

        @NotNull(message = "{pokemon.genId.notNull}")
        @Min(value = 1, message = "{pokemon.genId.rangeMin}")
        @Max(value = 10, message = "{pokemon.genId.rangeMax}")
        Integer generationId,

        @NotBlank(message = "{pokemon.name.notBlank}")
        @Size(min = 3, message = "{pokemon.name.rangeMin}")
        @Size(max = 255, message = "{pokemon.name.rangeMax}")
        String name,

        @Size(min = 1, message = "{pokemon.variant.rangeMin}")
        @Size(max = 255, message = "{pokemon.variant.rangeMax}")
        String variant,

        @Valid
        @NotNull(message = "{pokemon.type.notNull}")
        PokemonTypeDuo pokemonTypes,

        @NotNull(message = "{pokemon.rarity.notNull}")
        PokemonRarity rarity,

        PokemonAvailability availability,

        Boolean validIndicator
) {
    public PokemonRequest {
        if (availability == null) {
            availability = new PokemonAvailability();
        }

        if (validIndicator == null) {
            validIndicator = true;
        }
    }
}
