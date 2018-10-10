package com.example.jwtserver.exception;

public class InvalidJWTTokenException extends RuntimeException {
    public InvalidJWTTokenException(String message) {
        super(message);
    }
}
