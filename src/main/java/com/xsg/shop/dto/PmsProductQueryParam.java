package com.xsg.shop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 产品搜索时的需用的参数
 * Created by xsg on 2020/2/14 16:13
 */
@Getter
@Setter
public class PmsProductQueryParam {

    @ApiModelProperty("关键词")
    private String keyword;

    @ApiModelProperty("上架状态")
    private Integer publishStatus;

    @ApiModelProperty("审核状态")
    private Integer verifyStatus;

    @ApiModelProperty("品牌id")
    private Long brandId;

    @ApiModelProperty("分类id")
    private Long classifyId;

    @ApiModelProperty(value = "货号")
    private String productSn;

}
