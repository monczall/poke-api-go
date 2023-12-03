package com.pokeapigo.core.module.auth.exception.exceptions;

public class EmailOrPasswordMismatch extends RuntimeException {
    public EmailOrPasswordMismatch(String message) {
        super(message);
    }
}
