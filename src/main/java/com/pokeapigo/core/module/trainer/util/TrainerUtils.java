package com.pokeapigo.core.module.trainer.util;

import com.pokeapigo.core.module.trainer.TrainerEntity;
import com.pokeapigo.core.module.trainer.TrainerRepository;
import com.pokeapigo.core.module.trainer.dto.request.TrainerRequest;
import com.pokeapigo.core.module.trainer.exception.FailedToGenerateFriendCodeException;
import com.pokeapigo.core.module.trainer.exception.TrainerLevelHigherThanMaxException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

import static com.pokeapigo.core.module.trainer.util.TrainerConstants.*;

@Component
public class TrainerUtils {

    private final TrainerRepository trainerRepository;

    private final MessageSource messageSource;

    public TrainerUtils(TrainerRepository trainerRepository, MessageSource messageSource) {
        this.trainerRepository = trainerRepository;
        this.messageSource = messageSource;
    }

    public TrainerEntity updateTrainerEntityData(TrainerEntity trainer, TrainerRequest request, Locale locale) {
        Integer requestedLevel = request.level();
        if (requestedLevel > TRAINER_MAX_LEVEL) {
            throw new TrainerLevelHigherThanMaxException(messageSource.getMessage(
                    "trainer.levelHigherThanMax", new Object[]{requestedLevel, TRAINER_MAX_LEVEL}, locale)
            );
        }

        trainer.setName(request.name());
        trainer.setLevel(requestedLevel);
        trainer.setTeam(request.team());
        trainer.setAvatarUrl(request.avatarUrl());

        return trainer;
    }

    public String generateFriendCode(Locale locale) {
        String generatedFriendCode = null;

        int tries = 0;
        while (tries < FRIEND_CODE_GEN_TRIES) {
            generatedFriendCode = RandomStringUtils.random(FRIEND_CODE_LENGTH, true, true);
            if (!trainerRepository.existsByFriendCode(generatedFriendCode)) {
                return generatedFriendCode;
            }
            tries++;
        }

        throw new FailedToGenerateFriendCodeException(
                messageSource.getMessage("trainer.exceededReasonableAmountOfTries", new Object[]{FRIEND_CODE_GEN_TRIES}, locale)
        );
    }

}
