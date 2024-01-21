package com.pokeapigo.core.common.utli.constants;

public final class Constants {

    public static final String PRIVATE_CONSTRUCTOR_EXCEPTION_MESSAGE = "This is utility class and cannot be instantiated";
    public static final String EMAIL_REGEXP = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}";

    private Constants() {
        throw new UnsupportedOperationException(PRIVATE_CONSTRUCTOR_EXCEPTION_MESSAGE);
    }
}
