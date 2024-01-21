package com.pokeapigo.core.module.trainer.dto.request;

import com.pokeapigo.core.module.trainer.util.enums.TrainerTeam;

public record TrainerRequest(
        String name,
        Integer level,
        TrainerTeam team,
        String avatarUrl
) {
}
