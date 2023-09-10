package com.pokeapigo.core.pokemon;

import com.pokeapigo.core.pokemon.util.enums.PokemonType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class PokemonTypeDuo {

    @Column(name = "type_one")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{pokemon.typeOne.notNull}")
    private PokemonType typeOne;

    @Column(name = "type_two")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{pokemon.typeTwo.notNull}")
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
