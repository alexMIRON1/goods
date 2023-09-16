package com.test.product.web.exception;

import com.test.product.service.exception.ScheduleFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

/**
 * responsible for handling errors which will be returned from controller in case throw exception.
 *
 * @author Oleksandr Myronenko
 */
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(ScheduleFailedException.class)
    public final ResponseEntity<Object> handleNoSuchEntityException(ScheduleFailedException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public final ResponseEntity<Object> handleWrongDataException(NoSuchElementException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<Object> handleWrongDataException(IllegalArgumentException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
