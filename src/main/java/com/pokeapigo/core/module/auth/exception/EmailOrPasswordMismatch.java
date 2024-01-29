package com.pokeapigo.core.module.auth.exception;

public class EmailOrPasswordMismatch extends RuntimeException {
    public EmailOrPasswordMismatch(String message) {
        super(message);
    }
}
