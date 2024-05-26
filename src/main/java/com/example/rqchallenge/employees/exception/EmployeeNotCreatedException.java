package com.example.rqchallenge.employees.exception;

public class EmployeeNotCreatedException extends RuntimeException{

    String message;

    public EmployeeNotCreatedException(String message) {
        super(message);
        this.message = message;
    }
}
