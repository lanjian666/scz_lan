package com.lan.model;

import javax.persistence.*;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import lombok.*;
/**
 * @author lanjian
 * 旅游项目实体
 * */
@Entity
@Table(name = "project",
        indexes = {@Index(name = "i_project", columnList = "project_id")})
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "旅游项目实体")
public class Project {
    @Id
    @Column(name = "project_id",length=32)
    @GeneratedValue
    private  Integer  projectId;
    @Column(name = "project_name",nullable=false,length=30)
    private  String projectName;
    /**项目编号*/
    @Column(name = "project_code",nullable=false,length=50)
    private String projectCode;
    /**有效*/
    @Column(name = "valid",nullable=false,length=2)
    private Integer valid=1;
    /**开始日期*/
    @Column(name = "begin_date")
    private Date  beginDate;
    /**结束日期*/
    @Column(name = "end_date")
    private Date  endDate;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name="upload_address",length=254)
    private  String uploadAddress;
    @Column(name="project_abstract",length=254)
    private  String note;

}
