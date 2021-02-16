package com.xsg.shop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


/**
 * Created by xsg on 2020/4/16 10:27
 */
@Getter
@Setter
public class PmsProductBrandparam {
    @NotEmpty
    private String name;

    @ApiModelProperty(value = "首字母标识")
    private String initials;

    private Integer showStatu;

    private String logo;

    @ApiModelProperty(value = "专区图片")
    private String picture;

    private String description;
}
