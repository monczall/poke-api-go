package com.pokeapigo.core.module.trainer.dto.response;

import com.pokeapigo.core.module.trainer.util.enums.TrainerTeam;
import com.pokeapigo.core.role.RoleEntity;

import java.util.Set;
import java.util.UUID;

public record LimitedTrainerResponse(
        UUID trainerId,
        String name,
        Integer level,
        TrainerTeam team,
        String avatarUrl,
        String friendCode,
        Set<RoleEntity> role
) {
}
