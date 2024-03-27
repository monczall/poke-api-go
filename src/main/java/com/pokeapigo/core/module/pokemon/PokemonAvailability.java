package com.pokeapigo.core.module.pokemon;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PokemonAvailability {

    @Column(name = "AVAILABLE")
    @Schema(description = "Flag describing if Pokemon is released")
    private boolean available;

    @Column(name = "SHINY")
    @Schema(description = "Flag describing if Pokemon is available in shiny variant")
    private boolean shiny;

    @Column(name = "MEGA")
    @Schema(description = "Flag describing if Pokemon is available in mega variant")
    private boolean mega;

    @Column(name = "MEGAFAMILY")
    @Schema(description = "Flag describing if Pokemon is from mega family")
    private boolean megaFamily;

    @Column(name = "SHADOW")
    @Schema(description = "Flag describing if Pokemon is available in shadow variant")
    private boolean shadow;

    @Column(name = "TRADEEVOLVE")
    @Schema(description = "Flag describing if Pokemon is available in mega variant")
    private boolean tradeEvolve;

    @Column(name = "TRADEEVOLVEFAMILY")
    @Schema(description = "Flag describing if Pokemon is from tradeevolve family")
    private boolean tradeEvolveFamily;

    @Column(name = "TRADEABLE")
    @Schema(description = "Flag describing if Pokemon is available to trade")
    private boolean tradeable;

    @Column(name = "RAIDABLE")
    @Schema(description = "Flag describing if Pokemon is available in raids")
    private boolean raidable;

    @Column(name = "ALTERNATEFORM")
    @Schema(description = "Flag describing if Pokemon variant is it's alternate form")
    private boolean alternateForm;

    @Column(name = "COSTUMEFORM")
    @Schema(description = "Flag describing if Pokemon variant is it's costume form")
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
