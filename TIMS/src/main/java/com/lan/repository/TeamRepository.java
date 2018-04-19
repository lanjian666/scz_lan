package com.lan.repository;

import com.lan.model.Team;
import com.lan.util.MyRuntimeException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface TeamRepository extends JpaRepository<Team,Integer>,JpaSpecificationExecutor {
    /**
     * 通过id更新项目是否禁用或启用
     *
     * @author  lanjian
     * @param valid 禁用或启用
     * @param  id 项目id
     *
     * */
    @Transactional(rollbackOn = MyRuntimeException.class)
    @Modifying
    @Query(value = "update Team t set t.valid = ?1 where t.id = ?2")
    void updateValidById(Integer valid,Integer id);
}
