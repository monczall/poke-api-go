package com.pokeapigo.core.pokemon;

import com.pokeapigo.core.pokemon.util.enums.PokemonType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
class PokemonTypeDuo {

    @Column(name = "type_one")
    private PokemonType typeOne;

    @Column(name = "type_two")
    private PokemonType typeTwo;

    public PokemonType getTypeOne() {
        return typeOne;
    }

    public PokemonType getTypeTwo() {
        return typeTwo;
    }
}
