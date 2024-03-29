package com.pokeapigo.core.common.util.constants;

import static com.pokeapigo.core.common.util.constants.Constants.PRIVATE_CONSTRUCTOR_EXCEPTION_MESSAGE;

public final class ApiConstants {
    public static final String API = "/api";
    public static final String API_VERSION_1 = "/v1";
    public static final String API_URI_V1 = API + API_VERSION_1;
    public static final String URI_SECURED = "/secured";
    public static final String URI_AUTH = "/auth";
    public static final String URI_REGISTER = "/register";
    public static final String URI_LOGIN = "/login";
    public static final String URI_POKEMONS = "/pokemons";
    public static final String URI_TRAINERS = "/trainers";
    public static final String URI_CSV = "/csv";

    private ApiConstants() {
        throw new UnsupportedOperationException(PRIVATE_CONSTRUCTOR_EXCEPTION_MESSAGE);
    }
}
