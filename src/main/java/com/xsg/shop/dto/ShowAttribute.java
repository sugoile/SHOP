package com.xsg.shop.dto;

import com.xsg.shop.mbg.model.PmsProductAttribute;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 在前端展示页面的参数
 * Created by xsg on 2020/4/13 21:55
 */
@Getter
@Setter
public class ShowAttribute extends PmsProductAttribute {
    private String Classifyname;
    private List<String> InputLists;
}
