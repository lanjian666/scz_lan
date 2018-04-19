package com.lan.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
/**
 * 用来接受查询参数
 *
 * @author lanjian
 */
@ApiModel("通过多个id修改禁用或启用")
public class ValidByIds {
    @ApiModelProperty(value = "多个id组成的字符串")
    private String checkedIds;
    @ApiModelProperty(value = "禁用或用")
    private  Integer valid;
}
