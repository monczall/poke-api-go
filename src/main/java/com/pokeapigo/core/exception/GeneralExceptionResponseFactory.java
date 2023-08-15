package com.pokeapigo.core.exception;

import com.pokeapigo.core.exception.dto.BasicErrorDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

public class GeneralExceptionResponseFactory {

    private GeneralExceptionResponseFactory() {
    }

    public static ResponseEntity<BasicErrorDto> getBasicErrorDtoResponse(RuntimeException e, HttpServletRequest request,
                                                                         HttpStatus responseStatus) {
        final var basicErrorDto = new BasicErrorDto(
                Instant.now(),
                responseStatus.value(),
                responseStatus.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(responseStatus)
                .body(basicErrorDto);
    }

}
