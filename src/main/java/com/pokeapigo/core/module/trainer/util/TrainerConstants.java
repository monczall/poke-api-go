package com.pokeapigo.core.module.trainer.util;

import static com.pokeapigo.core.common.utli.constants.Constants.PRIVATE_CONSTRUCTOR_EXCEPTION_MESSAGE;

public final class TrainerConstants {
    public static final Integer TRAINER_MIN_LEVEL = 1;
    public static final Integer TRAINER_MAX_LEVEL = 50;
    public static final Integer FRIEND_CODE_LENGTH = 12;
    public static final Integer FRIEND_CODE_GEN_TRIES = 5;
    public static final Integer TRAINER_PAGE_MAX = 100;

    private TrainerConstants() {
        throw new UnsupportedOperationException(PRIVATE_CONSTRUCTOR_EXCEPTION_MESSAGE);
    }
}
