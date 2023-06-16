package com.example.jobis.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    public static <T> ResponseEntity initResponseEntity(T response){
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static <T> ResponseEntity initResponseEntity(T response, HttpStatus status){
        return new ResponseEntity<>(response, status);
    }
}
