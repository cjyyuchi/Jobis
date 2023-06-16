package com.example.jobis.entity;

import com.example.jobis.util.ColumnCryptor;
import lombok.Getter;

import javax.persistence.*;

@Table(name = "MEMBER")
@Entity
@Getter
public class MemberEntity {

    @Id
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "PASSWORD")
    @Convert(converter = ColumnCryptor.class)
    private String password;
    @Column(name = "NAME")
    private String name;
    @Column(name = "REGNO")
    @Convert(converter = ColumnCryptor.class)
    private String regNo;

    public MemberEntity(String userId, String password, String name, String regNo){
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.regNo = regNo;
    }

    public MemberEntity() {}
}
