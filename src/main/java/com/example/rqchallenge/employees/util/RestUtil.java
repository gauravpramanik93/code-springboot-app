package com.example.rqchallenge.employees.util;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RestUtil {

    @Bean
    public static RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Retry(name = "getCall")
    public static <T> T getCall(String url, Class<T> classType){
        log.info("Calling REST API : {}", url);
        return restTemplate().getForObject(url, classType);
    }

    @Retry(name = "postCall")
    public static <T> T postCall(String url, Object request, Class<T> classType){
        log.info("Calling REST API : {}", url);
        return restTemplate().postForObject(url, request, classType);
    }

    @Retry(name = "deleteCall")
    public static void deleteCall(String url){
        log.info("Calling REST API : {}",  url);
        restTemplate().delete(url);
    }

}

