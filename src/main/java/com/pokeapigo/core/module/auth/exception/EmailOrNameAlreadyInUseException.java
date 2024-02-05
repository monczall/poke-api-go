package com.pokeapigo.core.module.auth.exception;

public class EmailOrNameAlreadyInUseException extends RuntimeException {
    public EmailOrNameAlreadyInUseException(String message) {
        super(message);
    }
}
