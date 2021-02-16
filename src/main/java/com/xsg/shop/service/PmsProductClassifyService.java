package com.xsg.shop.service;

import com.xsg.shop.dto.ClassifyCascader;
import com.xsg.shop.dto.ClassifyTree;
import com.xsg.shop.dto.PmsProductClassifyParam;
import com.xsg.shop.mbg.model.PmsProductClassify;

import java.util.List;

/**
 * Created by xsg on 2020/4/7 9:46
 */
public interface PmsProductClassifyService {
    int create(PmsProductClassifyParam pmsProductClassifyParam);


    List<PmsProductClassify> getOnelevelList( Integer PageNum, Integer PageSize, Long id);

    ClassifyTree GetTreebyClassifyId(Long ID);

    int DeleteClassify(Long ID);

    int updateNavstatu(Long ID, Integer navStatu);

    int updateShowstatu(Long ID, Integer showStatu);

    PmsProductClassify GetClassifyById(Long ID);

    List<ClassifyTree> GetTree();

    int update(Long Classifyid, PmsProductClassifyParam pmsProductClassifyParam);

    List<Long> GetParentById(Long ID);

    List<ClassifyCascader> ClassifyCascaderTree();

    List<PmsProductClassify> getThreelevelList();
}
