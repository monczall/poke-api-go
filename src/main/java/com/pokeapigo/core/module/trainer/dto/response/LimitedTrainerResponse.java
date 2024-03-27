package com.pokeapigo.core.module.trainer.dto.response;

import com.pokeapigo.core.module.trainer.util.enums.TrainerTeam;
import com.pokeapigo.core.role.RoleEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;
import java.util.UUID;

@Schema(description = "Response model for Trainer containing all but sensitive data")
public record LimitedTrainerResponse(
        @Schema(description = "UUID of Trainer")
        UUID trainerId,

        @Schema(description = "Name of Trainer")
        String name,

        @Schema(description = "Level of Trainer")
        Integer level,

        @Schema(description = "Team of Trainer, ex. 'Instinct', 'Mystic', 'Valor'")
        TrainerTeam team,

        @Schema(description = "Url pointing to Trainer's profile picture")
        String avatarUrl,

        @Schema(description = "Friend Code assigned to Trainer")
        String friendCode,

        @Schema(description = "Set of Trainer's roles")
        Set<RoleEntity> role
) {
}
