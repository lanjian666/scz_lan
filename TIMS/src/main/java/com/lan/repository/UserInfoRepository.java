package com.lan.repository;

import com.lan.model.Team;
import com.lan.model.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserInfoRepository extends JpaRepository<UserInformation,Integer>,JpaSpecificationExecutor {
UserInformation findByUsername(String name);
}
