package com.pokeapigo.core.module.trainer.util;

import com.pokeapigo.core.module.trainer.TrainerRepository;
import com.pokeapigo.core.module.trainer.exception.FailedToGenerateFriendCodeInReasonableAmountOfTries;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

import static com.pokeapigo.core.module.trainer.util.TrainerConstants.FRIEND_CODE_GEN_TRIES;

@Component
public class TrainerUtils {

    private final TrainerRepository trainerRepository;

    private final MessageSource messageSource;

    public TrainerUtils(TrainerRepository trainerRepository, MessageSource messageSource) {
        this.trainerRepository = trainerRepository;
        this.messageSource = messageSource;
    }

    public String generateFriendCode(Locale locale) {
        String generatedFriendCode = null;

        int tries = 0;
        while (tries < FRIEND_CODE_GEN_TRIES) {
            generatedFriendCode = RandomStringUtils.random(16, true, true);
            if (!trainerRepository.existsByFriendCode(generatedFriendCode)) {
                return generatedFriendCode;
            }
            tries++;
        }

        throw new FailedToGenerateFriendCodeInReasonableAmountOfTries(
                messageSource.getMessage("trainer.exceededReasonableAmountOfTries", new Object[]{FRIEND_CODE_GEN_TRIES}, locale)
        );
    }
}
