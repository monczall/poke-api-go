package com.pokeapigo.core.common.utli.constants;

import static com.pokeapigo.core.common.utli.constants.Constants.PRIVATE_CONSTRUCTOR_EXCEPTION_MESSAGE;

public final class ApiConstants {
    public static final String API = "/api";
    public static final String API_VERSION_1 = "/v1";
    public static final String API_URI_V1 = API + API_VERSION_1;
    public static final String API_AUTH = "/auth";
    public static final String API_REGISTER = "/register";
    public static final String API_LOGIN = "/login";
    public static final String API_POKEMONS = "/pokemons";
    public static final String API_TRAINERS = "/trainers";

    private ApiConstants() {
        throw new UnsupportedOperationException(PRIVATE_CONSTRUCTOR_EXCEPTION_MESSAGE);
    }
}
