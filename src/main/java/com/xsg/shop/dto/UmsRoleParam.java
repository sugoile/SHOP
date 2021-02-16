package com.xsg.shop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * 角色的传入参数
 * Created by xsg on 2020/4/1 14:56
 */
@Getter
@Setter
public class UmsRoleParam {
    @ApiModelProperty(value = "名称")
    @NotEmpty
    private String name;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "启用状态：0->禁用；1->启用")
    private Integer status;
}
