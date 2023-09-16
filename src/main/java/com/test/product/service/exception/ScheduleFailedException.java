package com.test.product.service.exception;

/**
 * exception in case when scheduling job was failed
 *
 * @author Oleksandr Myronenko
 */
public class ScheduleFailedException extends RuntimeException {
    public ScheduleFailedException() {
    }

    public ScheduleFailedException(String message) {
        super(message);
    }
}
