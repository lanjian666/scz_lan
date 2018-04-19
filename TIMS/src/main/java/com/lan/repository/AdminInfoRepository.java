package com.lan.repository;

import com.lan.model.AdminInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdminInfoRepository extends JpaRepository<AdminInfo,Integer>,JpaSpecificationExecutor  {
    AdminInfo findByUsername(String name);
}
