package com.example.jobis.exception;

import lombok.Getter;

@Getter
public class BusinessException extends Exception {

    int code;
    public BusinessException(int code, String message){
        super(message);
        this.code = code;
    }
}
