package com.pokeapigo.core.module.trainer.exception.handler;

import com.pokeapigo.core.exception.dto.BasicErrorDto;
import com.pokeapigo.core.module.trainer.exception.FailedToGenerateFriendCodeInReasonableAmountOfTries;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.pokeapigo.core.exception.GeneralExceptionResponseFactory.getBasicErrorDtoResponse;

@ControllerAdvice
public class TrainerExceptionHandler {

    @ExceptionHandler({FailedToGenerateFriendCodeInReasonableAmountOfTries.class})
    ResponseEntity<BasicErrorDto> failedToGenerateFriendCodeInReasonableAmountOfTries(RuntimeException ex,
                                                                                      HttpServletRequest request) {
        return getBasicErrorDtoResponse(ex, request, HttpStatus.LOOP_DETECTED);
    }
}
