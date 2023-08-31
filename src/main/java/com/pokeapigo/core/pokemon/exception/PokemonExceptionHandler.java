package com.pokeapigo.core.pokemon.exception;

import com.pokeapigo.core.exception.dto.BasicErrorDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import static com.pokeapigo.core.exception.GeneralExceptionResponseFactory.getBasicErrorDtoResponse;


@ControllerAdvice
public class PokemonExceptionHandler {

    @ExceptionHandler
    ResponseEntity<BasicErrorDto> pokemonAlreadyExists(RuntimeException ex, HttpServletRequest request) {
        return getBasicErrorDtoResponse(ex, request, HttpStatus.CONFLICT);
    }
}
