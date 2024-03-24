package com.pokeapigo.core.module.csv.exception.handler;

import com.pokeapigo.core.exception.dto.BasicErrorDto;
import com.pokeapigo.core.module.csv.exception.CSVParserException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.pokeapigo.core.exception.util.GeneralExceptionResponseFactory.getBasicErrorDtoResponse;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class CSVExceptionHandler {

    @ExceptionHandler({
            CSVParserException.class,
    })
    ResponseEntity<BasicErrorDto> generalBadRequestException(RuntimeException e, HttpServletRequest request) {
        return getBasicErrorDtoResponse(e, request, BAD_REQUEST);
    }
}
