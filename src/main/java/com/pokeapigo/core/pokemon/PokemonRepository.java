package com.pokeapigo.core.pokemon;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PokemonRepository extends JpaRepository<PokemonEntity, UUID> {

    @Query("""
            SELECT p
            FROM Pokemon p
            WHERE
                p.visible = true
            ORDER BY
                pokedexId ASC,
                name ASC
            """)
    List<PokemonEntity> findAllOrderByPokedexIdAscNameAscAndVisibleTrue();

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
    Page<PokemonEntity> findAllByNameOrderByPokedexIdAscNameAsc(Pageable pageable, String name);

    @Query("""
            SELECT p
            FROM Pokemon p
            WHERE (
                :name IS null
                OR
                lower(p.name) LIKE lower(concat('%', :name, '%'))
                )
                AND
                p.visible = true
            ORDER BY
                pokedexId ASC,
                name ASC
            """)
    Page<PokemonEntity> findAllByNameOrderByPokedexIdAscNameAscAndVisibleTrue(Pageable pageable, String name);

    @Query("""
            SELECT p
            FROM Pokemon p
            WHERE (
                p.pokedexId = :pokedexId
                AND
                lower(p.name) = lower(:name)
            )
            """)
    Optional<PokemonEntity> findByPokedexIdAndNameIgnoreCase(Integer pokedexId, String name);

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
    Optional<PokemonEntity> findByPokedexIdAndNameIgnoreCaseAndVisibleTrue(Integer pokedexId, String name);
}
