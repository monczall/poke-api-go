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
                :name IS null
                OR
                lower(p.name) LIKE lower(concat('%', :searchPhrase, '%'))
            )
            AND
                p.visible = true
            ORDER BY
                pokedexId ASC,
                name ASC
            """)
    Page<PokemonEntity> findVisibleFilteredAndPaged(Pageable pageable, String searchPhrase);

    @Query("""
            SELECT
                CASE WHEN COUNT(p) > 0
                    THEN true
                    ELSE false
                END
            FROM PokemonEntity p
                WHERE (
                    (
                        cast(:pokemonUUID as org.hibernate.type.PostgresUUIDType) IS null
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
