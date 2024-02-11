package com.pokeapigo.core.exception.handler;

import com.pokeapigo.core.exception.CSVParserException;
import com.pokeapigo.core.exception.InvalidColumnNameException;
import com.pokeapigo.core.exception.OtherDataAccessApiException;
import com.pokeapigo.core.exception.dto.BasicErrorDto;
import com.pokeapigo.core.exception.dto.ValidationErrorDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static com.pokeapigo.core.exception.GeneralExceptionResponseFactory.getBasicErrorDtoResponse;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
class GeneralExceptionHandler {

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            InvalidColumnNameException.class,
            OtherDataAccessApiException.class,
            MethodArgumentTypeMismatchException.class,
            CSVParserException.class,
            MaxUploadSizeExceededException.class
    })
    ResponseEntity<BasicErrorDto> generalBadRequestException(RuntimeException e, HttpServletRequest request) {
        return getBasicErrorDtoResponse(e, request, BAD_REQUEST);
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class
    })
    ResponseEntity<ValidationErrorDto> validationError(MethodArgumentNotValidException e, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ValidationErrorDto errorDto = new ValidationErrorDto(
                Instant.now(),
                BAD_REQUEST.value(),
                BAD_REQUEST.getReasonPhrase(),
                errors,
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorDto, BAD_REQUEST);
    }
}
