package com.pokeapigo.core.pokemon;

import com.pokeapigo.core.pokemon.util.enums.PokemonRarity;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "pokemons")
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private PokemonRarity rarity;

    @Embedded
    private PokemonAvailability availability;

    public Pokemon() {
    }

    public Pokemon(Integer pokedexId, Integer generationId, String name, String variant, PokemonTypeDuo pokemonTypes,
                   PokemonRarity rarity, PokemonAvailability availability) {
        this.pokedexId = pokedexId;
        this.generationId = generationId;
        this.name = name;
        this.variant = variant;
        this.pokemonTypes = pokemonTypes;
        this.rarity = rarity;
        this.availability = availability;
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
}
