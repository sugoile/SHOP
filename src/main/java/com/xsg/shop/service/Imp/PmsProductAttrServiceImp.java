package com.xsg.shop.service.Imp;

import cn.hutool.core.bean.BeanUtil;
import com.xsg.shop.dto.PmsProductAttributeparam;
import com.xsg.shop.dto.ShowAttribute;
import com.xsg.shop.mbg.mapper.PmsProductAttributeMapper;
import com.xsg.shop.mbg.mapper.PmsProductAttributeValueMapper;
import com.xsg.shop.mbg.mapper.PmsProductClassifyMapper;
import com.xsg.shop.mbg.model.*;
import com.xsg.shop.service.PmsProductAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by xsg on 2020/4/13 21:08
 */
@Service
public class PmsProductAttrServiceImp implements PmsProductAttrService {
    @Autowired
    private PmsProductAttributeMapper productAttributeMapper;
    @Autowired
    private PmsProductClassifyMapper pmsProductClassifyMapper;
    @Autowired
    private PmsProductAttributeValueMapper pmsProductAttributeValueMapper;

    @Override
    public int add(PmsProductAttributeparam pmsProductAttributeparam) {
        PmsProductAttribute pmsProductAttribute = new PmsProductAttribute();
        BeanUtil.copyProperties(pmsProductAttributeparam, pmsProductAttribute);
        if(pmsProductAttribute.getType() == 0){
            pmsProductAttribute.setInputType(0);
            pmsProductAttribute.setSelectType(0);
        }
        return productAttributeMapper.insertSelective(pmsProductAttribute);
    }

    @Override
    public List<ShowAttribute> GetDynamicByClassifyid(Long classifyid) {
        PmsProductAttributeExample productAttributeExample = new PmsProductAttributeExample();
        //0代表查询全部的情况
        if(classifyid != 0) {
            productAttributeExample.createCriteria().andAttrClassifyIdEqualTo(classifyid);
        }
        List<PmsProductAttribute> pmsProductAttributes = productAttributeMapper.selectByExample(productAttributeExample);
        List<ShowAttribute> showAttributes = new ArrayList<>();
        pmsProductAttributes.forEach(productAttribute -> {
            if(productAttribute.getType() == 1) {
                ShowAttribute showAttribute = new ShowAttribute();
                BeanUtil.copyProperties(productAttribute, showAttribute);
                //返回各分类名(如: 女装/夹克/牛仔)
                PmsProductClassify pmsProductClassify = pmsProductClassifyMapper.selectByPrimaryKey(productAttribute.getAttrClassifyId());
                String classifyname = pmsProductClassify.getName();
                for (int i = 0; i < 2; i++) {
                    pmsProductClassify = pmsProductClassifyMapper.selectByPrimaryKey(pmsProductClassify.getParentid());
                    StringBuilder sb = new StringBuilder(classifyname);
                    sb.insert(0, pmsProductClassify.getName() + "/");
                    classifyname = sb.toString();
                }
                showAttribute.setClassifyname(classifyname);

                //返回动态值可选列表
                if (productAttribute.getInputList() != null && productAttribute.getInputList().length() != 0) {
                    String inputlist = productAttribute.getInputList();
                    List<String> lists = Arrays.asList(inputlist.split(","));
                    showAttribute.setInputLists(lists);
                }
                showAttributes.add(showAttribute);
            }
        });

        return showAttributes;
    }

    @Override
    public int update(Long id, PmsProductAttributeparam pmsProductAttributeparam) {
        PmsProductAttribute pmsProductAttribute = new PmsProductAttribute();
        BeanUtil.copyProperties(pmsProductAttributeparam, pmsProductAttribute);
        pmsProductAttribute.setId(id);
        System.out.println(pmsProductAttribute);
        return productAttributeMapper.updateByPrimaryKeySelective(pmsProductAttribute);
    }

    @Override
    public int delete(Long id) {
        PmsProductAttributeValueExample pmsProductAttributeValueExample = new PmsProductAttributeValueExample();
        pmsProductAttributeValueExample.createCriteria().andProductAttrIdEqualTo(id);
        pmsProductAttributeValueMapper.deleteByExample(pmsProductAttributeValueExample);
        return productAttributeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public  List<String> deleteValue(Long id, String tag) {
        PmsProductAttribute pmsProductAttribute = productAttributeMapper.selectByPrimaryKey(id);
        String inputlist = pmsProductAttribute.getInputList();
        String[] arr = inputlist.split(",");
        List<String> lists = new ArrayList<>();
        for(int i = 0; i<arr.length; i++){
           if(!arr[i].equals(tag)) {
               lists.add(arr[i]);
           }
        }
        String returnlist = lists.stream().collect(Collectors.joining(","));
        PmsProductAttribute attribute = new PmsProductAttribute();
        BeanUtil.copyProperties(pmsProductAttribute, attribute);
        attribute.setId(id);
        attribute.setInputList(returnlist);
        int count =  productAttributeMapper.updateByPrimaryKey(attribute);
        if(count > 0){
            return lists;
        } else{
            return null;
        }
    }

    @Override
    public List<String> addDynamicValue(Long Attrid, String value) {
        PmsProductAttribute pmsProductAttribute = productAttributeMapper.selectByPrimaryKey(Attrid);
        String inputlist = pmsProductAttribute.getInputList();
        if(inputlist == null || inputlist.length() == 0){
            pmsProductAttribute.setInputList(value);
        }
        else{
            pmsProductAttribute.setInputList(inputlist + "," + value);
        }
        int count = productAttributeMapper.updateByPrimaryKey(pmsProductAttribute);
        List<String> lists = new ArrayList<>();
        if(count > 0){
            if(inputlist == null || inputlist.length() == 0){
                inputlist = value;
                lists.add(inputlist);
            } else {
                inputlist += ("," + value);
                lists = Arrays.asList(inputlist.split(","));
            }
        }
        return lists;
    }

    @Override
    public List<ShowAttribute> GetStaticByClassifyid(Long classifyid) {
        PmsProductAttributeExample productAttributeExample = new PmsProductAttributeExample();
        //0代表查询全部的情况
        if(classifyid != 0) {
            productAttributeExample.createCriteria().andAttrClassifyIdEqualTo(classifyid);
        }
        List<PmsProductAttribute> pmsProductAttributes = productAttributeMapper.selectByExample(productAttributeExample);
        List<ShowAttribute> showAttributes = new ArrayList<>();
        pmsProductAttributes.forEach(productAttribute -> {
            if(productAttribute.getType() == 0) {
                ShowAttribute showAttribute = new ShowAttribute();
                BeanUtil.copyProperties(productAttribute, showAttribute);
                //返回各分类名(如: 女装/夹克/牛仔)
                PmsProductClassify pmsProductClassify = pmsProductClassifyMapper.selectByPrimaryKey(productAttribute.getAttrClassifyId());
                String classifyname = pmsProductClassify.getName();
                for (int i = 0; i < 2; i++) {
                    pmsProductClassify = pmsProductClassifyMapper.selectByPrimaryKey(pmsProductClassify.getParentid());
                    StringBuilder SB = new StringBuilder(classifyname);
                    SB.insert(0, pmsProductClassify.getName() + "/");
                    classifyname = SB.toString();
                }
                showAttribute.setClassifyname(classifyname);

                showAttributes.add(showAttribute);
            }
        });
        return showAttributes;
    }
}
