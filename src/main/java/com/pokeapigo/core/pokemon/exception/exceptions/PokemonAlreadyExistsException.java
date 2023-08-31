package com.pokeapigo.core.pokemon.exception.exceptions;

public class PokemonAlreadyExistsException extends RuntimeException {
    public PokemonAlreadyExistsException(String message) {
        super(message);
    }
}
