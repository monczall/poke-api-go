package com.pokeapigo.core.pokemon;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PokemonRepository extends JpaRepository<Pokemon, UUID> {

    @Query("""
            SELECT p
            FROM Pokemon p
            ORDER BY
                pokedexId ASC,
                name ASC
            """)
    List<Pokemon> findAllOrderByPokedexIdAscNameAsc();

    @Query("""
            SELECT p
            FROM Pokemon p
            WHERE (
                :name IS null
                OR
                lower(p.name) LIKE lower(concat('%', :name, '%'))
            )
            ORDER BY
                pokedexId ASC,
                name ASC
            """)
    Page<Pokemon> findAllByNameOrderByPokedexIdAscNameAsc(Pageable pageable, String name);

    @Query("""
            SELECT p
            FROM Pokemon p
            WHERE (
                p.pokedexId = :pokedexId
                AND
                lower(p.name) = lower(:name)
            )
            """)
    Optional<Pokemon> findByPokedexIdAndNameIgnoreCase(Integer pokedexId, String name);

    @Query("""
            SELECT p
            FROM Pokemon p
            WHERE (
                p.pokedexId = :pokedexId
                AND
                lower(p.name) = lower(:name)
                AND
                p.visible = true
            )
            """)
    Optional<Pokemon> findByPokedexIdAndNameIgnoreCaseAndVisibleTrue(Integer pokedexId, String name);
}
