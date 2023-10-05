package com.pokeapigo.core.module.pokemon;

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
    List<PokemonEntity> findAllVisibleByOrder();

    @Query("""
            SELECT p
            FROM PokemonEntity p
            WHERE (
                :search IS null
                OR
                CAST(p.pokedexId AS string) = :search
                OR
                lower(p.name) LIKE lower(concat('%', :search, '%'))
                OR
                lower(p.variant) LIKE lower(concat('%', :search, '%'))
                OR
                lower(p.pokemonTypes.typeOne) = lower(:search)
                OR
                lower(p.pokemonTypes.typeTwo) = lower(:search)
                OR
                lower(p.rarity) = lower(:search)
            )
            AND
                p.visible = true
            """)
    Page<PokemonEntity> findVisibleFilteredAndPaged(Pageable pageable, String search);

    @Query("""
            SELECT
                CASE WHEN COUNT(p) > 0
                    THEN true
                    ELSE false
                END
            FROM PokemonEntity p
            WHERE (
                (
                    cast(:pokemonUUID as string) IS null
                    OR
                    p.id != :pokemonUUID
                )
                AND
                p.pokedexId = :pokedexId
                AND
                lower(p.name) = lower(:name)
                AND (
                    (
                        :variant IS null
                        AND
                        p.variant IS null
                    )
                    OR
                    lower(p.variant) = lower(:variant)
                )
            )
            """)
    boolean pokemonExists(UUID pokemonUUID, Integer pokedexId, String name, String variant);
}
