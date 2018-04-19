package com.lan.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用来存储分页信息和旅游项目集合
 *
 * @author lanjian
 */
@ApiModel(value = "分页可项目集合")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageAndProjectsVo {
    @ApiModelProperty(value = "当前分页信息")
    private PageObjectVO pageObject;
    @ApiModelProperty(value = "存放旅游项目的集合")
    private List<ProjectVO> list;
}
