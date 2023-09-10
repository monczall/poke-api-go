package com.pokeapigo.core.pokemon;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PokemonAvailability {

    @JsonProperty
    @Column(name = "is_wild")
    private boolean isWild;

    @JsonProperty
    @Column(name = "is_raid")
    private boolean isRaid;

    @JsonProperty
    @Column(name = "is_egg")
    private boolean isEgg;

    @JsonProperty
    @Column(name = "is_shadow")
    private boolean isShadow;

    @JsonProperty
    @Column(name = "is_mega")
    private boolean isMega;

    public PokemonAvailability() {
    }

    public PokemonAvailability(boolean isWild, boolean isRaid, boolean isEgg, boolean isShadow, boolean isMega) {
        this.isWild = isWild;
        this.isRaid = isRaid;
        this.isEgg = isEgg;
        this.isShadow = isShadow;
        this.isMega = isMega;
    }

    @Override
    public String toString() {
        return "PokemonAvailability{" +
                "isWild=" + isWild +
                ", isRaid=" + isRaid +
                ", isEgg=" + isEgg +
                ", isShadow=" + isShadow +
                ", isMega=" + isMega +
                '}';
    }
}
