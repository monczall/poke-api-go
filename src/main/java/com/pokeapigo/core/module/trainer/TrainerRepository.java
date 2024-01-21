package com.pokeapigo.core.module.trainer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrainerRepository extends JpaRepository<TrainerEntity, UUID> {

    Optional<TrainerEntity> findByEmail(String email);

    boolean existsByFriendCode(String friendCode);
}
