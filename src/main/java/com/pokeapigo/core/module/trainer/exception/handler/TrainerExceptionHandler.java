package com.pokeapigo.core.module.trainer.exception.handler;

import com.pokeapigo.core.exception.dto.BasicErrorDto;
import com.pokeapigo.core.module.trainer.exception.FailedToGenerateFriendCodeException;
import com.pokeapigo.core.module.trainer.exception.InvalidTrainerLevelException;
import com.pokeapigo.core.module.trainer.exception.TrainerAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.pokeapigo.core.exception.util.GeneralExceptionResponseFactory.getBasicErrorDtoResponse;

@ControllerAdvice
public class TrainerExceptionHandler {

    @ExceptionHandler({
            FailedToGenerateFriendCodeException.class
    })
    ResponseEntity<BasicErrorDto> trainerLoopDetectionException(RuntimeException ex,
                                                                HttpServletRequest request) {
        return getBasicErrorDtoResponse(ex, request, HttpStatus.LOOP_DETECTED);
    }

    @ExceptionHandler({
            InvalidTrainerLevelException.class,
            TrainerAlreadyExistsException.class
    })
    ResponseEntity<BasicErrorDto> trainerBadRequestException(RuntimeException ex,
                                                             HttpServletRequest request) {
        return getBasicErrorDtoResponse(ex, request, HttpStatus.BAD_REQUEST);
    }
}
