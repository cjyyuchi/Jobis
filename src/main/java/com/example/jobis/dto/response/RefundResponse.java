package com.example.jobis.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RefundResponse {

    public RefundResponse(String name, String determinedTaxAmount, String retirementPensionTaxCredit){
        this.name = name;
        this.determinedTaxAmount = determinedTaxAmount;
        this.retirementPensionTaxCredit = retirementPensionTaxCredit;
    }

    @Schema(description = "이름", example = "홍길동")
    @JsonProperty(value = "이름")
    private String name;
    @Schema(description = "결정세액", example = "15,000")
    @JsonProperty(value = "결정세액")
    private String determinedTaxAmount;
    @Schema(description = "퇴직연금세액공제", example = "5,000")
    @JsonProperty(value = "퇴직연금세액공제")
    private String retirementPensionTaxCredit;

}
