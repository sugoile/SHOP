package com.xsg.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 返回给前端建立el-cascader的数据
 * Created by xsg on 2020/4/13 14:25
 */
@Getter
@Setter
@AllArgsConstructor
public class ClassifyCascader {
    private String label;
    private Long value;
    private Long pid;
    private List<ClassifyCascader> children;
}
