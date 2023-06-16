package com.example.jobis.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginResponse {

    @Schema(description = "응답코드", example = "200")
    private int code;
    @Schema(description = "응답메시지", example = "Success")
    private String message;
    @Schema(description = "토큰정보", example = "eyJhbGciOiJub25lIn0.eyJ1c2VySWQiOiJob25nMTIiLCJwYXNzd29yZCI6IjEyMzQ1NiIsIm5hbWUiOiLtmY3quLjrj5kiLCJyZWdObyI6Ijg2MDgyNC0xNjU1MDY4In0.")
    private String token;

    public LoginResponse(String token){
        this.code = 200;
        this.message = "Success";
        this.token = token;
    }


}
