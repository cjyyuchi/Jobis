package com.example.jobis.dto.request.szs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
@Schema(description = "회원가입 입력정보")
public class SignUp {

    @NotEmpty
    @Schema(description = "아이디", example = "hong12", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userId;
    @NotEmpty
    @Schema(description = "패스워드", example = "123456", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
    @NotEmpty
    @Schema(description = "이름", example = "홍길동", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    @NotEmpty
    @Schema(description = "주민등록번호", example = "860824-1655068", requiredMode = Schema.RequiredMode.REQUIRED)
    private String regNo;

}
