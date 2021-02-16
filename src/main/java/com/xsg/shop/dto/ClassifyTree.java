package com.xsg.shop.dto;

import com.xsg.shop.mbg.model.PmsProductClassify;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by xsg on 2020/4/7 20:06
 */

public class ClassifyTree extends PmsProductClassify {
    @Getter
    @Setter
    List<ClassifyTree> children;
}
