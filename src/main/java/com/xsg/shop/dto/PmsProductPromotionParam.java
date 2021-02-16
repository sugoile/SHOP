package com.xsg.shop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by xsg on 2020/4/24 15:48
 */
@Getter
@Setter
public class PmsProductPromotionParam {
    @ApiModelProperty(value = "促销价格")
    private BigDecimal promotionPrice;

    @ApiModelProperty(value = "促销开始时间")
    private String promotionStart;

    @ApiModelProperty(value = "促销结束时间")
    private String promotionEnd;
}
