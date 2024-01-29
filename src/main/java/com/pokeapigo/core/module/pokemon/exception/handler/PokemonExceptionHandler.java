package com.pokeapigo.core.module.pokemon.exception.handler;

import com.pokeapigo.core.exception.dto.BasicErrorDto;
import com.pokeapigo.core.module.pokemon.exception.PokemonAlreadyExistsException;
import com.pokeapigo.core.module.pokemon.exception.PokemonNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.pokeapigo.core.exception.GeneralExceptionResponseFactory.getBasicErrorDtoResponse;

@ControllerAdvice
public class PokemonExceptionHandler {

    @ExceptionHandler({
            PokemonNotFoundException.class
    })
    ResponseEntity<BasicErrorDto> pokemonNotFound(RuntimeException ex, HttpServletRequest request) {
        return getBasicErrorDtoResponse(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({PokemonAlreadyExistsException.class})
    ResponseEntity<BasicErrorDto> pokemonAlreadyExists(RuntimeException ex, HttpServletRequest request) {
        return getBasicErrorDtoResponse(ex, request, HttpStatus.CONFLICT);
    }
}
