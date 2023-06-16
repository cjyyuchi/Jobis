package com.example.jobis.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter @Setter
public class SzsScrap {

    private String userId;
    private String incomeClassification;
    private BigDecimal amount;

}
