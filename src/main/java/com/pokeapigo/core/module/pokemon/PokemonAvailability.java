package com.pokeapigo.core.module.pokemon;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PokemonAvailability {

    @Column(name = "available")
    private boolean available;

    @Column(name = "shiny")
    private boolean shiny;

    @Column(name = "mega")
    private boolean mega;

    @Column(name = "mega_family")
    private boolean megaFamily;

    @Column(name = "shadow")
    private boolean shadow;

    @Column(name = "trade_evolve")
    private boolean tradeEvolve;

    @Column(name = "trade_evolve_family")
    private boolean tradeEvolveFamily;

    @Column(name = "tradeable")
    private boolean tradeable;

    @Column(name = "raidable")
    private boolean raidable;

    @Column(name = "alternate_form")
    private boolean alternateForm;

    @Column(name = "costume_form")
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
