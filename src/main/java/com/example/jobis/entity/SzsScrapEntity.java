package com.example.jobis.entity;

import com.example.jobis.util.ColumnCryptor;
import lombok.Getter;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "SZS_SCRAP", indexes = @Index(name="SZS_SCRAP_IX_USERID", columnList = "USER_ID"))
@Entity
@Getter
public class SzsScrapEntity {

    public SzsScrapEntity() {}

    public SzsScrapEntity(String userId, String incomeClassification, BigDecimal amount){
        this.userId = userId;
        this.incomeClassification = incomeClassification;
        this.amount = amount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "INCOME_CLASSIFICATION")
    private String incomeClassification;
    @Column(name = "AMOUNT")
    private BigDecimal amount;


}
