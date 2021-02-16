package com.xsg.shop.dto;

import com.xsg.shop.validator.FlagValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * 参数传入
 * Created by xsg on 2020/4/13 20:58
 */
@Getter
@Setter
public class PmsProductAttributeparam {
    private Long attrClassifyId;
    @NotEmpty
    private String name;
    @ApiModelProperty(value = "选择类型：0->固定; 1->单选; 2->多选")
    private Integer selectType;
    @ApiModelProperty(value = "类型: 0->规格参数(静态); 1->属性列表(动态)")
    @FlagValidator(value = {"0", "1"}, message = "传入类型不正确")
    private Integer type;
}
