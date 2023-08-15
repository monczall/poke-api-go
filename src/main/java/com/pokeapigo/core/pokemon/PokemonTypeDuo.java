package com.pokeapigo.core.pokemon;

import com.pokeapigo.core.pokemon.util.enums.PokemonType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

import static com.pokeapigo.core.pokemon.util.constants.PokemonErrorConstants.POKEMON_TYPE_ONE_ERROR_MESSAGE;
import static com.pokeapigo.core.pokemon.util.constants.PokemonErrorConstants.POKEMON_TYPE_TWO_ERROR_MESSAGE;

@Embeddable
public class PokemonTypeDuo {

    @Column(name = "type_one")
    @Enumerated(EnumType.STRING)
    @NotNull(message = POKEMON_TYPE_ONE_ERROR_MESSAGE)
    private PokemonType typeOne;

    @Column(name = "type_two")
    @Enumerated(EnumType.STRING)
    @NotNull(message = POKEMON_TYPE_TWO_ERROR_MESSAGE)
    private PokemonType typeTwo;

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
