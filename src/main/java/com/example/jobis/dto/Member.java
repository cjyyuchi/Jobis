package com.example.jobis.dto;

import com.example.jobis.util.ColumnCryptor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;

@Getter @Setter
public class Member {

    private String userId;
    private String password;
    private String name;
    private String regNo;
}
