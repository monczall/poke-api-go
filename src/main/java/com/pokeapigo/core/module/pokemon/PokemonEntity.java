package com.pokeapigo.core.module.pokemon;

import com.pokeapigo.core.module.pokemon.util.enums.PokemonRarity;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "POK_POKEMONS")
public class PokemonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "POKEDEXID")
    private Integer pokedexId;

    @Column(name = "GENERATIONID")
    private Integer generationId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "VARIANT")
    private String variant;

    @Embedded
    private PokemonTypeDuo pokemonTypes;

    @Column(name = "RARITY")
    @Enumerated(EnumType.STRING)
    private PokemonRarity rarity;

    @Embedded
    private PokemonAvailability availability;

    @Column(name = "VALIDINDICATOR")
    private Boolean validIndicator;

    public PokemonEntity() {
    }

    public PokemonEntity(Integer pokedexId, Integer generationId, String name, String variant, PokemonTypeDuo pokemonTypes,
                         PokemonRarity rarity, PokemonAvailability availability, Boolean validIndicator) {
        this.pokedexId = pokedexId;
        this.generationId = generationId;
        this.name = name;
        this.variant = variant;
        this.pokemonTypes = pokemonTypes;
        this.rarity = rarity;
        this.availability = availability;
        this.validIndicator = validIndicator;
    }

    public UUID getId() {
        return id;
    }

    public Integer getPokedexId() {
        return pokedexId;
    }

    public Integer getGenerationId() {
        return generationId;
    }

    public String getName() {
        return name;
    }

    public String getVariant() {
        return variant;
    }

    public PokemonTypeDuo getPokemonTypes() {
        return pokemonTypes;
    }

    public PokemonRarity getRarity() {
        return rarity;
    }

    public PokemonAvailability getAvailability() {
        return availability;
    }

    public Boolean getValidIndicator() {
        return validIndicator;
    }

    public void setPokedexId(Integer pokedexId) {
        this.pokedexId = pokedexId;
    }

    public void setGenerationId(Integer generationId) {
        this.generationId = generationId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public void setPokemonTypes(PokemonTypeDuo pokemonTypes) {
        this.pokemonTypes = pokemonTypes;
    }

    public void setRarity(PokemonRarity rarity) {
        this.rarity = rarity;
    }

    public void setAvailability(PokemonAvailability availability) {
        this.availability = availability;
    }

    public void setValidIndicator(Boolean validIndicator) {
        this.validIndicator = validIndicator;
    }

    @Override
    public String toString() {
        return "PokemonEntity{" +
                "id=" + id +
                ", pokedexId=" + pokedexId +
                ", generationId=" + generationId +
                ", name='" + name + '\'' +
                ", variant='" + variant + '\'' +
                ", pokemonTypes=" + pokemonTypes +
                ", rarity=" + rarity +
                ", availability=" + availability +
                ", validIndicator=" + validIndicator +
                '}';
    }
}
