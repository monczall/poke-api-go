package com.pokeapigo.core.module.trainer.dto.request;

import com.pokeapigo.core.module.trainer.util.enums.TrainerTeam;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Schema(description = "Request model for Trainer updates")
public record TrainerRequest(
        @NotBlank(message = "{trainer.name.notBlank}")
        @Size(min = 3, message = "{trainer.name.rangeMin}")
        @Size(max = 35, message = "{trainer.name.rangeMax}")
        @Schema(description = "Name of updated Trainer", requiredMode = REQUIRED)
        String name,

        @NotNull(message = "{trainer.level.notNull}")
        @Min(value = 1, message = "{trainer.level.rangeMin}")
        @Max(value = 50, message = "{trainer.level.rangeMax}")
        @Schema(description = "Level of updated Trainer", requiredMode = REQUIRED)
        Integer level,

        @NotNull(message = "{trainer.team.notNull}")
        @Schema(description = "Team of updated Trainer, ex. 'Instinct', 'Mystic', 'Valor'", requiredMode = REQUIRED)
        TrainerTeam team,

        @Min(value = 10, message = "{trainer.avatarUrl.rangeMin}")
        @Max(value = 4096, message = "{trainer.avatarUrl.rangeMax}")
        @Schema(description = "Url pointing to Trainer's profile picture")
        String avatarUrl
) {
}
