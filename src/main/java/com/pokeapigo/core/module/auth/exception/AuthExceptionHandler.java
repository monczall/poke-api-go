package com.pokeapigo.core.module.auth.exception;

import com.pokeapigo.core.exception.dto.BasicErrorDto;
import com.pokeapigo.core.module.auth.exception.exceptions.EmailOrPasswordMismatch;
import com.pokeapigo.core.module.auth.exception.exceptions.PasswordsDoNotMatchException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.pokeapigo.core.exception.GeneralExceptionResponseFactory.getBasicErrorDtoResponse;

@ControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler({
            PasswordsDoNotMatchException.class,
            EmailOrPasswordMismatch.class
    })
    ResponseEntity<BasicErrorDto> badRequestExceptions(RuntimeException ex, HttpServletRequest request) {
        return getBasicErrorDtoResponse(ex, request, HttpStatus.BAD_REQUEST);
    }
}
