package com.pokeapigo.core.module.trainer.exception;

public class TrainerAlreadyActiveException extends RuntimeException {
    public TrainerAlreadyActiveException(String message) {
        super(message);
    }
}
