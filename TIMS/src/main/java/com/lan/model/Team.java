package com.lan.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**用于封装团信息:
 * 业务:一个项目下可以有多个团
 * 表关系:one2many
 * 表设计:关联字段projectId
 * 应该添加多的一端,例如在tms_teams表中添加projectId
 * */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "旅游团目管理")
public class Team implements Serializable {
    private static final long serialVersionUID = 368009064732092303L;
/**
     * 团的id
     */

    @Id
    @Column(name = "team_id", length = 32)
    @GeneratedValue
    private Integer id;
/**
     * 团名称
     */

    @Column(name = "team_name", nullable = false, length = 30)
 private String name;
 /**
     * 项目id(关联项目模块的项目id)
     */

    @Column(name = "project_id", nullable = false, length = 30)
    private Integer projectId;
    /**
     * 项目名称(关联项目模块的项目id)
     */

    @Column(name = "project_name", length = 30)
    private String projectName;
/**
     * 有效性(启动[1]和禁用[0])
     */

    @Column(name = "valid", nullable = false, length = 2)
    private Integer valid;
/**
     * 备注
     */

    @Column(name = "note",length=60)
    private String note;
/**
     * 创建时间
     */

    @Column(name = "created_time")
    private Date createdTime;
/**
     * 修改时间
     */

    @Column(name = "modified_time")
    private Date modifiedTime;

}
