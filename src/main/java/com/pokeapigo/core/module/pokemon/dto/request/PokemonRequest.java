package com.pokeapigo.core.module.pokemon.dto.request;

import com.pokeapigo.core.module.pokemon.PokemonAvailability;
import com.pokeapigo.core.module.pokemon.PokemonTypeDuo;
import com.pokeapigo.core.module.pokemon.util.enums.PokemonRarity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Schema(description = "Request model for Pokemon creation and updates")
public record PokemonRequest(
        @NotNull(message = "{pokemon.dexId.notnull}")
        @Min(value = 1, message = "{pokemon.dexId.rangeMin}")
        @Max(value = 1200, message = "{pokemon.dexId.rangeMax}")
        @Schema(description = "Pokedex ID of newly created Pokemon", requiredMode = REQUIRED)
        Integer pokedexId,

        @NotNull(message = "{pokemon.genId.notNull}")
        @Min(value = 1, message = "{pokemon.genId.rangeMin}")
        @Max(value = 10, message = "{pokemon.genId.rangeMax}")
        @Schema(description = "Generation ID of newly created Pokemon", requiredMode = REQUIRED)
        Integer generationId,

        @NotBlank(message = "{pokemon.name.notBlank}")
        @Size(min = 3, message = "{pokemon.name.rangeMin}")
        @Size(max = 255, message = "{pokemon.name.rangeMax}")
        @Schema(description = "Name of newly created Pokemon", requiredMode = REQUIRED)
        String name,

        @Size(min = 1, message = "{pokemon.variant.rangeMin}")
        @Size(max = 255, message = "{pokemon.variant.rangeMax}")
        @Schema(description = "Variant of newly created Pokemon, ex. 'Party Hat', 'Alola', etc.")
        String variant,

        @Valid
        @NotNull(message = "{pokemon.type.notNull}")
        @Schema(description = "Object containing both types of newly created Pokemon", requiredMode = REQUIRED)
        PokemonTypeDuo pokemonTypes,

        @NotNull(message = "{pokemon.rarity.notNull}")
        @Schema(description = "Rarity of newly created Pokemon, ex. 'Normal', 'Legendary', 'Mythic'", requiredMode = REQUIRED)
        PokemonRarity rarity,

        @Schema(description = "Object containing different Pokemon available flags")
        PokemonAvailability availability,

        @Schema(description = "Flag describing if newly created Pokemon is valid or not")
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
