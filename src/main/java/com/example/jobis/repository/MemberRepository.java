package com.example.jobis.repository;

import com.example.jobis.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {

    public MemberEntity findFirstByUserIdAndPassword(String userId, String password);

    public MemberEntity findFirstByUserId(String userId);

    public MemberEntity findFirstByNameAndRegNo(String name, String regNo);

    public boolean existsByUserId(String userId);


}
