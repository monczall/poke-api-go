package com.pokeapigo.core.module.pokemon.exception;

public class PokemonAlreadyExistsException extends RuntimeException {
    public PokemonAlreadyExistsException(String message) {
        super(message);
    }
}
