package com.pokeapigo.core.common.utli.constants;

public class ApiConstants {
    public static final String API = "/api";
    public static final String API_VERSION_1 = "/v1";
    public static final String API_URI_V1 = API + API_VERSION_1;
    public static final String API_POKEMONS = "/pokemons";

    private ApiConstants() {
        throw new UnsupportedOperationException("This is utility class and cannot be instantiated");
    }
}
