package com.pokeapigo.core.module.pokemon;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PokemonAvailability {

    @Column(name = "AVAILABLE")
    private boolean available;

    @Column(name = "SHINY")
    private boolean shiny;

    @Column(name = "MEGA")
    private boolean mega;

    @Column(name = "MEGAFAMILY")
    private boolean megaFamily;

    @Column(name = "SHADOW")
    private boolean shadow;

    @Column(name = "TRADEEVOLVE")
    private boolean tradeEvolve;

    @Column(name = "TRADEEVOLVEFAMILY")
    private boolean tradeEvolveFamily;

    @Column(name = "TRADEABLE")
    private boolean tradeable;

    @Column(name = "RAIDABLE")
    private boolean raidable;

    @Column(name = "ALTERNATEFORM")
    private boolean alternateForm;

    @Column(name = "COSTUMEFORM")
    private boolean costumeForm;

    public PokemonAvailability() {
    }

    public PokemonAvailability(boolean available, boolean shiny, boolean mega, boolean megaFamily, boolean shadow,
                               boolean tradeEvolve, boolean tradeEvolveFamily, boolean tradeable, boolean raidable,
                               boolean alternateForm, boolean costumeForm) {
        this.available = available;
        this.shiny = shiny;
        this.mega = mega;
        this.megaFamily = megaFamily;
        this.shadow = shadow;
        this.tradeEvolve = tradeEvolve;
        this.tradeEvolveFamily = tradeEvolveFamily;
        this.tradeable = tradeable;
        this.raidable = raidable;
        this.alternateForm = alternateForm;
        this.costumeForm = costumeForm;
    }

    public boolean isAvailable() {
        return available;
    }

    public boolean isShiny() {
        return shiny;
    }

    public boolean isMega() {
        return mega;
    }

    public boolean isMegaFamily() {
        return megaFamily;
    }

    public boolean isShadow() {
        return shadow;
    }

    public boolean isTradeEvolve() {
        return tradeEvolve;
    }

    public boolean isTradeEvolveFamily() {
        return tradeEvolveFamily;
    }

    public boolean isTradeable() {
        return tradeable;
    }

    public boolean isRaidable() {
        return raidable;
    }

    public boolean isAlternateForm() {
        return alternateForm;
    }

    public boolean isCostumeForm() {
        return costumeForm;
    }

    @Override
    public String toString() {
        return "PokemonAvailability{" +
                "available=" + available +
                ", shiny=" + shiny +
                ", mega=" + mega +
                ", megaFamily=" + megaFamily +
                ", shadow=" + shadow +
                ", tradeEvolve=" + tradeEvolve +
                ", tradeEvolveFamily=" + tradeEvolveFamily +
                ", tradeable=" + tradeable +
                ", raidable=" + raidable +
                ", alternateForm=" + alternateForm +
                ", costumeForm=" + costumeForm +
                '}';
    }
}
