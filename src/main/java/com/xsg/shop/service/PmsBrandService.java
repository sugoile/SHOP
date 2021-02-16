package com.xsg.shop.service;

import com.xsg.shop.dto.PmsProductBrandparam;
import com.xsg.shop.mbg.model.PmsBrand;

import java.util.List;

/**
 * Created by xsg on 2020/4/15 22:02
 */
public interface PmsBrandService {
    int add(PmsProductBrandparam pmsProductBrandparam);

    List<PmsBrand> getListBrand(String query, Integer pageSize,Integer pageNum);

    int updateShowstatu(Long ID, Integer showStatu);

    int DeleteBrand(Long ID);

    int deleteList(List<Long>ids);

    PmsBrand GetBrandById(Long ID);

    int deletepic(Long ID);

    int openShowStatuList(List<Long>ids);

    int closeShowStatuList(List<Long>ids);

    int update(Long id, PmsProductBrandparam pmsProductBrandparam);

    List<PmsBrand> getBrand();

}
