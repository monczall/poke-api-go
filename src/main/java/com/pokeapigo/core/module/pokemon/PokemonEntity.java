package com.pokeapigo.core.module.pokemon;

import com.pokeapigo.core.module.pokemon.util.enums.PokemonRarity;
import jakarta.persistence.*;

import java.util.UUID;

import static com.pokeapigo.core.common.utli.constants.DataBaseConstants.POKEMONS_TABLE;

@Entity
@Table(name = POKEMONS_TABLE)
public class PokemonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;

    @Column(name = "pokedex_id")
    private Integer pokedexId;

    @Column(name = "generation_id")
    private Integer generationId;

    @Column(name = "name")
    private String name;

    @Column(name = "variant")
    private String variant;

    @Embedded
    private PokemonTypeDuo pokemonTypes;

    @Column(name = "rarity")
    @Enumerated(EnumType.STRING)
    private PokemonRarity rarity;

    @Embedded
    private PokemonAvailability availability;

    private Boolean visible;

    public PokemonEntity() {
    }

    public PokemonEntity(Integer pokedexId, Integer generationId, String name, String variant, PokemonTypeDuo pokemonTypes,
                         PokemonRarity rarity, PokemonAvailability availability) {
        this.pokedexId = pokedexId;
        this.generationId = generationId;
        this.name = name;
        this.variant = variant;
        this.pokemonTypes = pokemonTypes;
        this.rarity = rarity;
        this.availability = availability;
        this.visible = true;
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

    public Boolean getVisible() {
        return visible;
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

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", pokedexId=" + pokedexId +
                ", generationId=" + generationId +
                ", name='" + name + '\'' +
                ", variant='" + variant + '\'' +
                ", pokemonTypes=" + pokemonTypes +
                ", rarity=" + rarity +
                ", availability=" + availability +
                '}';
    }
}
