package com.pokeapigo.core.module.trainer.exception;

public class FailedToGenerateFriendCodeInReasonableAmountOfTries extends RuntimeException {
    public FailedToGenerateFriendCodeInReasonableAmountOfTries(String message) {
        super(message);
    }
}
