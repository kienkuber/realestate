package com.example.realestate.controller.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
@Slf4j
public class ExceptionTranslator {

    @ExceptionHandler(value = { NoSuchElementException.class })
    public ResponseEntity<Object> handleException(Exception ex) {
        log.error("Exception: {} ",ex.getMessage());
        return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
