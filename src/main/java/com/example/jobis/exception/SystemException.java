package com.example.jobis.exception;

import lombok.Getter;

@Getter
public class SystemException extends Exception {

    int code;
    public SystemException(int code, String message){
        super(message);
        this.code = code;
    }
}
