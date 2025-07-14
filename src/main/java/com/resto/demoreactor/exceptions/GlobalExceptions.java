package com.resto.demoreactor.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;


@RestControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(RuntimeException.class)
    public Mono<ResponseEntity<String>> ejecutandoException(RuntimeException ex){
        return Mono.just(ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("el error es "+ex.getMessage()));
    }

}
