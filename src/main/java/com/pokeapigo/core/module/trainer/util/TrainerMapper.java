package com.pokeapigo.core.module.trainer.util;

import com.pokeapigo.core.module.trainer.TrainerEntity;
import com.pokeapigo.core.module.trainer.dto.response.FullTrainerResponse;
import com.pokeapigo.core.module.trainer.dto.response.LimitedTrainerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Optional;


public class TrainerMapper {

    public static FullTrainerResponse toFullTrainerResponse(TrainerEntity trainerEntity) {
        return Optional.ofNullable(trainerEntity).map(trainer -> new FullTrainerResponse(
                        trainer.getId(),
                        trainer.getName(),
                        trainer.getLevel(),
                        trainer.getTeam(),
                        trainer.getAvatarUrl(),
                        trainer.getFriendCode(),
                        trainer.getUsername(),
                        trainer.getRoles()
                )
        ).orElse(null);
    }

    public static LimitedTrainerResponse toLimitedTrainerResponse(TrainerEntity trainerEntity) {
        return Optional.ofNullable(trainerEntity).map(trainer -> new LimitedTrainerResponse(
                        trainer.getId(),
                        trainer.getName(),
                        trainer.getLevel(),
                        trainer.getTeam(),
                        trainer.getAvatarUrl(),
                        trainer.getFriendCode(),
                        trainer.getRoles()
                )
        ).orElse(null);
    }

    public static Page<LimitedTrainerResponse> toPagedLimitedTrainerResponse(Page<TrainerEntity> pagedTrainers) {
        return new PageImpl<>(
                pagedTrainers.getContent().stream().map(trainer ->
                        new LimitedTrainerResponse(
                                trainer.getId(),
                                trainer.getName(),
                                trainer.getLevel(),
                                trainer.getTeam(),
                                trainer.getAvatarUrl(),
                                trainer.getFriendCode(),
                                trainer.getRoles())
                ).toList()
        );
    }

    private TrainerMapper() {
    }
}
