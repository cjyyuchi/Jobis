package com.example.jobis.dto.response;

import com.example.jobis.dto.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MeResponse {

    @Schema(description = "응답코드", example = "200")
    private int code;
    @Schema(description = "응답메시지", example = "Success")
    private String message;
    @Schema(description = "회원정보")
    private Member result;

    public MeResponse(Member result){
        this.code = 200;
        this.message = "Success";
        this.result = result;
    }


}
