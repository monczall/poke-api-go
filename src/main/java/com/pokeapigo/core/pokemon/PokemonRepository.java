package com.pokeapigo.core.pokemon;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PokemonRepository extends JpaRepository<Pokemon, UUID> {

    Optional<Pokemon> findByPokedexIdAndNameIgnoreCase(Integer pokedexId, String name);

    Optional<Pokemon> findByPokedexIdAndNameIgnoreCaseAndVisibleTrue(Integer pokedexId, String name);
}
