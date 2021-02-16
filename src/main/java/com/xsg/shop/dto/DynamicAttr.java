package com.xsg.shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 动态传入参数（用于商品绑定的）
 * Created by xsg on 2020/4/21 9:53
 */
@Getter
@Setter
public class DynamicAttr {
    private Long id;
    private List<String> values;
}
