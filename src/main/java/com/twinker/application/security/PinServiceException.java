package com.twinker.application.security;

public class PinServiceException extends RuntimeException {
    public PinServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
