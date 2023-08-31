package com.pokeapigo.core.pokemon.dto.request;

import com.pokeapigo.core.pokemon.PokemonAvailability;
import com.pokeapigo.core.pokemon.PokemonTypeDuo;
import com.pokeapigo.core.pokemon.util.enums.PokemonRarity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import static com.pokeapigo.core.pokemon.util.PokemonConstants.*;

public record PokemonRequest(
        @NotNull(message = "{pokemon.dexId.notnull}")
        @Size(min = DEX_ID_MIN, max = DEX_ID_MAX, message = "{pokemon.dexId.range}")
        Integer pokedexId,

        @NotNull(message = "{pokemon.genId.notNull}")
        @Size(min = GEN_ID_MIN, max = GEN_ID_MAX, message = "{pokemon.genId.range}")
        Integer generationId,

        @NotBlank(message = "{pokemon.name.notBlank}")
        @Size(min = POK_NAME_MIN, max = POK_NAME_MAX, message = "{pokemon.name.range}")
        String name,

        @Valid
        @NotNull(message = "{pokemon.type.notNull}")
        PokemonTypeDuo pokemonTypes,

        @NotNull(message = "{pokemon.rarity.notNull}")
        PokemonRarity rarity,

        PokemonAvailability availability
) {
    public PokemonRequest {
        if (availability == null) {
            availability = new PokemonAvailability();
        }
    }
}
