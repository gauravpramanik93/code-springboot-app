package com.example.rqchallenge.employees.exception;

public class RestApiExecutionException extends RuntimeException {
    String message;

    public RestApiExecutionException(String message) {
        super(message);
        this.message = message;
    }
}
