package com.example.jobis.repository;

import com.example.jobis.entity.MemberWhiteListEntity;
import com.example.jobis.entity.SzsScrapEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SzsScrapRepository extends JpaRepository<SzsScrapEntity, String> {

    public void deleteByUserId(String userId);

    public List<SzsScrapEntity> findByUserId(String userId);

}
