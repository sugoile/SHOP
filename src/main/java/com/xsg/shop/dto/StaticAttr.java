package com.xsg.shop.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 静态传入参数（用于商品绑定的）
 * Created by xsg on 2020/4/21 9:55
 */
@Getter
@Setter
public class StaticAttr {
    private Long id;
    private String name;
    private String value;
}
