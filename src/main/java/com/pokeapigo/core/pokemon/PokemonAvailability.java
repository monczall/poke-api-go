package com.pokeapigo.core.pokemon;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
class PokemonAvailability {

    @Column(name = "is_wild")
    private boolean isWild;

    @Column(name = "is_raid")
    private boolean isRaid;

    @Column(name = "is_egg")
    private boolean isEgg;

    @Column(name = "is_shadow")
    private boolean isShadow;

    @Column(name = "is_mega")
    private boolean isMega;

    public boolean isWild() {
        return isWild;
    }

    public boolean isRaid() {
        return isRaid;
    }

    public boolean isEgg() {
        return isEgg;
    }

    public boolean isShadow() {
        return isShadow;
    }

    public boolean isMega() {
        return isMega;
    }
}
