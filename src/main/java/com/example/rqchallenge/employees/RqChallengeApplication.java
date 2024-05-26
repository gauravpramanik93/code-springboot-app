package com.example.rqchallenge.employees;

import com.example.rqchallenge.employees.exception.RestExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RestExceptionHandler.class)
public class RqChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(RqChallengeApplication.class, args);
    }

}
