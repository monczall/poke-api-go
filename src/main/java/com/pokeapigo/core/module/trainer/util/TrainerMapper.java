package com.pokeapigo.core.module.trainer.util;

import com.pokeapigo.core.module.trainer.TrainerEntity;
import com.pokeapigo.core.module.trainer.dto.response.FullTrainerResponse;
import com.pokeapigo.core.module.trainer.dto.response.LimitedTrainerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;


public class TrainerMapper {

    public static FullTrainerResponse toFullTrainerResponse(TrainerEntity trainerEntity) {
        return new FullTrainerResponse(
                trainerEntity.getId(),
                trainerEntity.getName(),
                trainerEntity.getLevel(),
                trainerEntity.getTeam(),
                trainerEntity.getAvatarUrl(),
                trainerEntity.getFriendCode(),
                trainerEntity.getUsername(),
                trainerEntity.getRoles()
        );
    }

    public static LimitedTrainerResponse toLimitedTrainerResponse(TrainerEntity trainerEntity) {
        return new LimitedTrainerResponse(
                trainerEntity.getId(),
                trainerEntity.getName(),
                trainerEntity.getLevel(),
                trainerEntity.getTeam(),
                trainerEntity.getAvatarUrl(),
                trainerEntity.getFriendCode(),
                trainerEntity.getRoles()
        );
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
