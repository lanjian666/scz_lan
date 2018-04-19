package com.lan.repository;

import com.lan.model.Project;
import com.lan.util.MyRuntimeException;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 *
 *
 *@author lanjian
 *
 */
public interface ProjectRepository extends JpaRepository<Project,Integer>,JpaSpecificationExecutor{
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
    @Query(value = "update Project p set p.valid = ?1 where p.projectId = ?2")
    void updateValidById(Integer valid,Integer id);
}
