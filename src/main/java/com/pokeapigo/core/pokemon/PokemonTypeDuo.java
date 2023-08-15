package com.pokeapigo.core.pokemon;

import com.pokeapigo.core.pokemon.util.enums.PokemonType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
class PokemonTypeDuo {

    @Column(name = "type_one")
    @Enumerated(EnumType.STRING)
    private PokemonType typeOne;

    @Column(name = "type_two")
    @Enumerated(EnumType.STRING)
    private PokemonType typeTwo;

    public PokemonType getTypeOne() {
        return typeOne;
    }

    public PokemonType getTypeTwo() {
        return typeTwo;
    }
}
