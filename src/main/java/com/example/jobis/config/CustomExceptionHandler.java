package com.example.jobis.config;

import com.example.jobis.dto.response.ErrorResponse;
import com.example.jobis.dto.response.GeneralResponse;
import com.example.jobis.exception.BusinessException;
import com.example.jobis.exception.SystemException;
import com.example.jobis.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    // TODO :: Exception 통합 관리 (응답코드, 로깅 ...)
    @ExceptionHandler({BusinessException.class, SystemException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> businessExceptionHandler(BusinessException e){

        log.error("ERROR :: {}", e.getMessage());

        return ResponseUtil.initResponseEntity(new ErrorResponse(e.getCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GeneralResponse> methodArgumentNotValidExceptionExceptionHandler(MethodArgumentNotValidException e){

        log.error("ERROR :: {}", e.getMessage());

        return ResponseUtil.initResponseEntity(new ErrorResponse(9999, e.getMessage()), HttpStatus.BAD_REQUEST);
    }



}
