package com.lan.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
/**
 * 用来存储分页信息
 *
 * @author lanjian
 */
@ApiModel("分页信息")
public class PageObjectVO implements Serializable {
    @ApiModelProperty(value = "当前页")
    private Integer pageCurrent;
    @ApiModelProperty(value = "每页最多能显示的记录数")
    private Integer pageSize;
    @ApiModelProperty(value = "总记录数")
    private Long rowCount;
    @ApiModelProperty(value = "总页数")
    private Integer pageCount;
    @ApiModelProperty(value = "上一页最后一条记录的位置")
    private Integer startIndex;
}
