package com.example.jobis.dto.request.szs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
@Schema(description = "로그인 입력정보")
public class Login {

    @NotEmpty
    @Schema(description = "아이디", example = "hong12", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userId;
    @NotEmpty
    @Schema(description = "패스워드", example = "123456", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

}
