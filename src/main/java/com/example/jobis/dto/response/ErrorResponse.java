package com.example.jobis.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorResponse {

    @Schema(description = "에러코드", example = "9001")
    private int code;
    @Schema(description = "에러메시지", example = "요청하신 회원정보가 존재하지 않습니다.")
    private String message;

    public ErrorResponse(int code, String message){
        this.code = code;
        this.message = message;
    }
}
