package com.xsg.shop.mbg.mapper;

import com.xsg.shop.mbg.model.PmsProductQuantityDiscount;
import com.xsg.shop.mbg.model.PmsProductQuantityDiscountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PmsProductQuantityDiscountMapper {
    long countByExample(PmsProductQuantityDiscountExample example);

    int deleteByExample(PmsProductQuantityDiscountExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PmsProductQuantityDiscount record);

    int insertSelective(PmsProductQuantityDiscount record);

    List<PmsProductQuantityDiscount> selectByExample(PmsProductQuantityDiscountExample example);

    PmsProductQuantityDiscount selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PmsProductQuantityDiscount record, @Param("example") PmsProductQuantityDiscountExample example);

    int updateByExample(@Param("record") PmsProductQuantityDiscount record, @Param("example") PmsProductQuantityDiscountExample example);

    int updateByPrimaryKeySelective(PmsProductQuantityDiscount record);

    int updateByPrimaryKey(PmsProductQuantityDiscount record);
}