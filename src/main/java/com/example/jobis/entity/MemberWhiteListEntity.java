package com.example.jobis.entity;

import javax.persistence.*;

@Table(name = "MEMBER_WHITELIST")
@Entity
public class MemberWhiteListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "REGNO")
//    @Convert(converter = ColumnCryptor.class)
    private String regNo;

}
