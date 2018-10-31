package com.bio.exception;

public class FlupException extends RuntimeException {
    public FlupException(String message) {
        super(message);
    }

    public FlupException(String message, Throwable cause) {
        super(message, cause);
    }
}
