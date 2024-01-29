package com.pokeapigo.core.module.trainer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrainerRepository extends JpaRepository<TrainerEntity, UUID> {

    Optional<TrainerEntity> findByEmail(String email);

    @Query("""
            SELECT t
            FROM TrainerEntity t
            WHERE (
                :search IS null
                OR
                CAST(t.name AS string) = :search
            )            
            """)
    Page<TrainerEntity> findBySearch(Pageable pageable, String search);

    @Query("""
            SELECT
                CASE WHEN COUNT(t) > 0
                    THEN true
                    ELSE false
                END
            FROM TrainerEntity t
            WHERE (
                t.id != :trainerUUID
                AND
                lower(t.name) = lower(:name)
            )
            """)
    boolean editedTrainerNameAlreadyOccupied(UUID trainerUUID, String name);

    boolean existsByFriendCode(String friendCode);
}
