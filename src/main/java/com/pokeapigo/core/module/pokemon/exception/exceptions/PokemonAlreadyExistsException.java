package com.pokeapigo.core.module.pokemon.exception.exceptions;

public class PokemonAlreadyExistsException extends RuntimeException {
    public PokemonAlreadyExistsException(String message) {
        super(message);
    }
}
