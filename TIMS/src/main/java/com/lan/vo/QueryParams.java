package com.lan.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 用来接受查询参数
 *
 * @author lanjian
 */
@ApiModel("查询请求")
public class QueryParams {
    @ApiModelProperty(value = "模糊查询项目名的字段")
    private String name;
    @ApiModelProperty(value = "是否禁用")
    private Integer valid;
    @ApiModelProperty(value = "页面大小")
    private  Integer pageSize;
    @ApiModelProperty(value = "当前多少页")
    private Integer pageCurrent;
}
