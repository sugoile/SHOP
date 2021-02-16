package com.xsg.shop.service;

import com.xsg.shop.dto.PmsProductAttributeparam;
import com.xsg.shop.dto.ShowAttribute;

import java.util.List;

/**
 * Created by xsg on 2020/4/13 21:08
 */
public interface PmsProductAttrService {
    int add(PmsProductAttributeparam pmsProductAttributeparam);

    List<ShowAttribute> GetDynamicByClassifyid(Long classifyid);

    int update(Long id, PmsProductAttributeparam pmsProductAttributeparam);

    int delete(Long id);

    List<String> deleteValue(Long id, String tag);

    List<String> addDynamicValue(Long Attrid, String value);

    List<ShowAttribute> GetStaticByClassifyid(Long classifyid);
}
