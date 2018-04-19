package com.lan.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "旅游VO")
@NoArgsConstructor
@AllArgsConstructor
@Data
/**
 * Project的VO，用来传递Project对象
 *
 * @author lanjian
 */

public class ProjectVO implements Serializable {

    @ApiModelProperty(value = "项目ID",required = true)
    private  Integer  projectId;
    @ApiModelProperty(value = "项目名字",allowableValues = "adc,def")
    private  String projectName;
    @ApiModelProperty(value = "项目编号")
    private String projectCode;
    @ApiModelProperty(value = "项目是否启用1为启用，0为禁用")
    private Integer valid;
    @ApiModelProperty(value = "项目开始时间")
    private Date  beginDate;
    @ApiModelProperty(value = "项目结束时间")
    private Date  endDate;
    @ApiModelProperty(value = "项目创建时的时间间")
    private Date createTime;
    @ApiModelProperty(value = "项目修改时的时间")
    private Date updateTime;
    @ApiModelProperty(value = "项目图片")
    private  String uploadAddress;
    @ApiModelProperty(value = "项目图片")
    private  String note;
}
