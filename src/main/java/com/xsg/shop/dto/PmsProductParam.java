package com.xsg.shop.dto;

import com.xsg.shop.mbg.model.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by xsg on 2020/4/21 9:31
 */
@Getter
@Setter
public class PmsProductParam extends PmsProduct {
    private List<DynamicAttr> productDynamicAttrList;
    private List<StaticAttr> productStaticAttrList;
    private List<PmsProductSkuStock> sku;
    private PmsProductPromotionParam productPromotionPrice;
    private Integer productMemberReduce;
    private List<PmsProductQuantityDiscount> productQuantityDiscount;
    private  List<PmsProductFullReduce> productFullReduce;
}
