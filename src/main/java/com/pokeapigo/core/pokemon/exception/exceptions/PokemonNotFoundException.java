package com.pokeapigo.core.pokemon.exception.exceptions;

public class PokemonNotFoundException extends RuntimeException {
    public PokemonNotFoundException(String message) {
        super(message);
    }
}
