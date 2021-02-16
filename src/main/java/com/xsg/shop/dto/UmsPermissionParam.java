package com.xsg.shop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户传入参数
 * Created by xsg on 2020/4/1 19:58
 */
@Getter
@Setter
public class UmsPermissionParam {
    @ApiModelProperty(value = "父级权限id")
    private Long parentid;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "权限值")
    private String value;

    @ApiModelProperty(value = "启用状态；0->禁用；1->启用")
    private Integer status;
}
