package com.xsg.shop.service.Imp;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.xsg.shop.dto.PmsProductBrandparam;
import com.xsg.shop.mbg.mapper.PmsBrandMapper;
import com.xsg.shop.mbg.model.PmsBrand;
import com.xsg.shop.mbg.model.PmsBrandExample;
import com.xsg.shop.service.PmsBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by xsg on 2020/4/15 22:03
 */
@Service
public class PmsBrandServiceImp implements PmsBrandService {
    @Autowired
    private PmsBrandMapper pmsBrandMapper;

    @Override
    public int add(PmsProductBrandparam pmsProductBrandparam) {
        PmsBrand pmsBrand = new PmsBrand();
        BeanUtil.copyProperties(pmsProductBrandparam, pmsBrand);
        PmsBrandExample pmsBrandExample = new PmsBrandExample();
        pmsBrandExample.createCriteria().andNameEqualTo(pmsProductBrandparam.getName());
        List<PmsBrand> pmsBrands = pmsBrandMapper.selectByExample(pmsBrandExample);
        if(pmsBrands != null && pmsBrands.size() != 0){
            return -1;
        }
        return pmsBrandMapper.insertSelective(pmsBrand);
    }

    @Override
    public List<PmsBrand> getListBrand(String query, Integer pageSize,Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        PmsBrandExample pmsBrandExample  = new PmsBrandExample();
        if(query == null || query.length() == 0){
            return pmsBrandMapper.selectByExample(pmsBrandExample);
        }
        else {
            pmsBrandExample.createCriteria().andNameLike("%" + query + "%");
            return pmsBrandMapper.selectByExample(pmsBrandExample);
        }
    }

    @Override
    public int updateShowstatu(Long ID, Integer showStatu) {
        PmsBrand pmsBrand = new PmsBrand();
        pmsBrand.setId(ID);
        pmsBrand.setShowStatu(showStatu);
        return pmsBrandMapper.updateByPrimaryKeySelective(pmsBrand);
    }

    @Override
    public int DeleteBrand(Long ID) {
        if(pmsBrandMapper.selectByPrimaryKey(ID) == null){
            return -1;
        } else{
            return pmsBrandMapper.deleteByPrimaryKey(ID);
        }
    }

    @Override
    public int deleteList(List<Long> ids) {
        PmsBrandExample pmsBrandExample = new PmsBrandExample();
        pmsBrandExample.createCriteria().andIdIn(ids);
        return pmsBrandMapper.deleteByExample(pmsBrandExample);
    }

    @Override
    public PmsBrand GetBrandById(Long ID) {
        return pmsBrandMapper.selectByPrimaryKey(ID);
    }

    @Override
    public int deletepic(Long ID) {
        PmsBrand pmsBrand = new PmsBrand();
        pmsBrand.setId(ID);
        pmsBrand.setLogo("");
        return pmsBrandMapper.updateByPrimaryKeySelective(pmsBrand);
    }

    @Override
    public int openShowStatuList(List<Long> ids) {
        PmsBrand pmsBrand = new PmsBrand();
        pmsBrand.setShowStatu(1);
        PmsBrandExample pmsBrandExample = new PmsBrandExample();
        pmsBrandExample.createCriteria().andIdIn(ids);
        return pmsBrandMapper.updateByExampleSelective(pmsBrand, pmsBrandExample);
    }

    @Override
    public int closeShowStatuList(List<Long> ids) {
        PmsBrand pmsBrand = new PmsBrand();
        pmsBrand.setShowStatu(0);
        PmsBrandExample pmsBrandExample = new PmsBrandExample();
        pmsBrandExample.createCriteria().andIdIn(ids);
        return pmsBrandMapper.updateByExampleSelective(pmsBrand, pmsBrandExample);
    }

    @Override
    public int update(Long id, PmsProductBrandparam pmsProductBrandparam) {
        PmsBrand pmsBrand = new PmsBrand();
        BeanUtil.copyProperties(pmsProductBrandparam, pmsBrand);
        pmsBrand.setId(id);
        return  pmsBrandMapper.updateByPrimaryKeySelective(pmsBrand);
    }

    @Override
    public List<PmsBrand> getBrand() {
        return pmsBrandMapper.selectByExample(new PmsBrandExample());
    }
}
