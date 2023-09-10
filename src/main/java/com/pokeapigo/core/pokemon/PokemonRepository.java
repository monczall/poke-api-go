package com.pokeapigo.core.pokemon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PokemonRepository extends JpaRepository<Pokemon, UUID> {

    @Query(
            """
                    SELECT p
                    FROM Pokemon p
                    ORDER BY
                        pokedexId ASC,
                        name ASC
                    """
    )
    List<Pokemon> findAllOrderByPokedexIdAscNameAsc();

    Optional<Pokemon> findByPokedexIdAndNameIgnoreCase(Integer pokedexId, String name);

    Optional<Pokemon> findByPokedexIdAndNameIgnoreCaseAndVisibleTrue(Integer pokedexId, String name);
}
