package com.pokeapigo.core.module.trainer.exception;

public class TrainerAlreadyDeactivatedException extends RuntimeException {
    public TrainerAlreadyDeactivatedException(String message) {
        super(message);
    }
}
