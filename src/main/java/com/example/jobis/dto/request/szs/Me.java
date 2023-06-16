package com.example.jobis.dto.request.szs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
@Schema(description = "토큰정보")
public class Me {

    @NotEmpty
    @Schema(description = "토큰", example = "eyJhbGciOiJub25lIn0.eyJ1c2VySWQiOiJob25nMTIiLCJwYXNzd29yZCI6IjEyMzQ1NiIsIm5hbWUiOiLtmY3quLjrj5kiLCJyZWdObyI6Ijg2MDgyNC0xNjU1MDY4In0.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String token;

}
