package com.pokeapigo.core.module.trainer.exception;

public class TrainerLevelHigherThanMaxException extends RuntimeException {
    public TrainerLevelHigherThanMaxException(String message) {
        super(message);
    }
}
