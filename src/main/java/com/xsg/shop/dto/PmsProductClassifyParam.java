package com.xsg.shop.dto;

import com.xsg.shop.validator.FlagValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * 商品分类传入参数
 * Created by xsg on 2020/4/7 9:47
 */
@Getter
@Setter
public class PmsProductClassifyParam {
    @ApiModelProperty(value = "上级分类的编号：0表示一级分类")
    private Long parentid;

    @ApiModelProperty(value = "商品分类名字")
    @NotEmpty(message = "分类名字不能为空")
    private String name;

    @ApiModelProperty(value = "分类单位")
    private String productUnit;

    @ApiModelProperty(value = "是否显示在导航栏：0->不显示；1->显示")
    @FlagValidator(value = {"0", "1"}, message = "导航栏显示状态不正确")
    private Integer navStatu;

    @ApiModelProperty(value = "显示状态：0->不显示；1->显示")
    @FlagValidator(value = {"0", "1"}, message = "显示状态不正确")
    private Integer showStatu;

    @ApiModelProperty(value = "图标")
    private String icon;
    @ApiModelProperty(value = "关键词")
    private String keyword;
    @ApiModelProperty(value = "描述")
    private String description;
}
