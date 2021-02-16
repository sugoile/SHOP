package com.xsg.shop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by xsg on 2020/4/22 15:45
 */
@Setter
@Getter
public class PmsProductSkuParam {
    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "预警库存")
    private Integer lowStock;

    @ApiModelProperty(value = "销量")
    private Integer sale;

    private String value;
}
