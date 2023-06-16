package com.example.jobis.dto.request.szs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
@Schema(description = "회원 세부정보 조회")
public class Scrap {

    @NotEmpty
    @Schema(description = "이름", example = "홍길동", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    @NotEmpty
    @Schema(description = "주민등록번호", example = "860824-1655068", requiredMode = Schema.RequiredMode.REQUIRED)
    private String regNo;

}
