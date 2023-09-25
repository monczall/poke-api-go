package com.pokeapigo.core.pokemon;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PokemonRepository extends JpaRepository<PokemonEntity, UUID> {

    @Query("""
            SELECT p
            FROM PokemonEntity p
            WHERE
                p.visible = true
            ORDER BY
                pokedexId ASC,
                name ASC
            """)
    List<PokemonEntity> findAllVisibleOrderByPokedexIdAndName();

    @Query("""
            SELECT p
            FROM PokemonEntity p
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
    Page<PokemonEntity> findAllVisibleByNameOrderByPokedexIdAndName(Pageable pageable, String name);

    @Query("""
            SELECT
                CASE WHEN COUNT(p) > 0
                    THEN true
                    ELSE false
                END
            FROM PokemonEntity p
                WHERE (
                    p.pokedexId = :pokedexId
                    AND
                    lower(p.name) LIKE lower(concat('%', :name, '%'))
                    AND (
                        :variant IS null
                        OR
                        lower(p.variant) LIKE lower(concat('%', :variant, '%'))
                    )
                )
            """)
    boolean pokemonExists(Integer pokedexId, String name, String variant);
}
