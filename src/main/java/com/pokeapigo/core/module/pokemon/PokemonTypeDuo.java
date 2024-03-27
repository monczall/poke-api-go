package com.pokeapigo.core.module.pokemon;

import com.pokeapigo.core.module.pokemon.util.enums.PokemonType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Embeddable
public class PokemonTypeDuo {

    @Column(name = "TYPEONE")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{pokemon.typeOne.notNull}")
    @Schema(description = "First type of newly created Pokemon", requiredMode = REQUIRED)
    private PokemonType typeOne;

    @Column(name = "TYPETWO")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{pokemon.typeTwo.notNull}")
    @Schema(description = "Second type of newly created Pokemon", requiredMode = REQUIRED)
    private PokemonType typeTwo;

    public PokemonTypeDuo() {
    }

    public PokemonTypeDuo(PokemonType typeOne, PokemonType typeTwo) {
        this.typeOne = typeOne;
        this.typeTwo = typeTwo;
    }

    public PokemonType getTypeOne() {
        return typeOne;
    }

    public PokemonType getTypeTwo() {
        return typeTwo;
    }

    @Override
    public String toString() {
        return "PokemonTypeDuo{" +
                "typeOne=" + typeOne +
                ", typeTwo=" + typeTwo +
                '}';
    }
}
