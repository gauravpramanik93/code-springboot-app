package com.example.rqchallenge.employees.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public @ResponseBody ErrorResponse
    handleException(EmployeeNotFoundException ex)
    {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(EmployeeNotCreatedException.class)
    public @ResponseBody ErrorResponse
    handleException(EmployeeNotCreatedException ex)
    {
        return new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

    @ExceptionHandler(RestApiExecutionException.class)
    public @ResponseBody ErrorResponse
    handleException(RestApiExecutionException ex)
    {
        return new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

}
