package com.pokeapigo.core.module.auth.exception;

public class EmailOrPasswordMismatchException extends RuntimeException {
    public EmailOrPasswordMismatchException(String message) {
        super(message);
    }
}
