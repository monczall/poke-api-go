package com.pokeapigo.core.module.trainer.dto.request;

import com.pokeapigo.core.module.trainer.util.enums.TrainerTeam;
import jakarta.validation.constraints.*;

public record TrainerRequest(
        @NotBlank(message = "{trainer.name.notBlank}")
        @Size(min = 3, message = "{trainer.name.rangeMin}")
        @Size(max = 35, message = "{trainer.name.rangeMax}")
        String name,

        @NotNull(message = "{trainer.level.notNull}")
        @Min(value = 1, message = "{trainer.level.rangeMin}")
        @Max(value = 50, message = "{trainer.level.rangeMax}")
        Integer level,

        @NotNull(message = "{trainer.team.notNull}")
        TrainerTeam team,

        @Min(value = 10, message = "{trainer.avatarUrl.rangeMin}")
        @Max(value = 4096, message = "{trainer.avatarUrl.rangeMax}")
        String avatarUrl
) {
}
