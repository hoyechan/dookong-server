package com.example.itrieone.exception;

public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException(String message) {
        super(message);
    }
}
