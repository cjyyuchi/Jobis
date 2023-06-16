package com.example.jobis.repository;

import com.example.jobis.entity.MemberWhiteListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberWhiteListRepository extends JpaRepository<MemberWhiteListEntity, String> {

    public boolean existsByNameAndRegNo(String name, String regNo);


}
