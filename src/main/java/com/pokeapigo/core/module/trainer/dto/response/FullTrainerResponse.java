package com.pokeapigo.core.module.trainer.dto.response;

import com.pokeapigo.core.module.trainer.util.enums.TrainerRole;
import com.pokeapigo.core.module.trainer.util.enums.TrainerTeam;

import java.util.UUID;

public record FullTrainerResponse(
        UUID trainerId,
        String name,
        Integer level,
        TrainerTeam team,
        String avatarUrl,
        String friendCode,
        String email,
        TrainerRole role
) {
}
