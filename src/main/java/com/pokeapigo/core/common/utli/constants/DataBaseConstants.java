package com.pokeapigo.core.common.utli.constants;

import static com.pokeapigo.core.common.utli.constants.Constants.PRIVATE_CONSTRUCTOR_EXCEPTION_MESSAGE;

public final class DataBaseConstants {
    public static final String POKEMONS_TABLE = "POK_POKEMONS";
    public static final String TRAINERS_TABLE = "TRA_TRAINERS";

    private DataBaseConstants() {
        throw new UnsupportedOperationException(PRIVATE_CONSTRUCTOR_EXCEPTION_MESSAGE);
    }
}
