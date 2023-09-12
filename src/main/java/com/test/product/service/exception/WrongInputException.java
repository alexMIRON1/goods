package com.test.product.service.exception;

/**
 * exception for wrong input from user
 *
 * @author Oleksandr Myronenko
 */
public class WrongInputException extends RuntimeException {
    public WrongInputException() {
    }

    public WrongInputException(String message) {
        super(message);
    }
}
