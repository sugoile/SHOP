package com.xsg.shop.mbg.mapper;

import com.xsg.shop.mbg.model.PmsProductSkuStock;
import com.xsg.shop.mbg.model.PmsProductSkuStockExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PmsProductSkuStockMapper {
    long countByExample(PmsProductSkuStockExample example);

    int deleteByExample(PmsProductSkuStockExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PmsProductSkuStock record);

    int insertSelective(PmsProductSkuStock record);

    List<PmsProductSkuStock> selectByExample(PmsProductSkuStockExample example);

    PmsProductSkuStock selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PmsProductSkuStock record, @Param("example") PmsProductSkuStockExample example);

    int updateByExample(@Param("record") PmsProductSkuStock record, @Param("example") PmsProductSkuStockExample example);

    int updateByPrimaryKeySelective(PmsProductSkuStock record);

    int updateByPrimaryKey(PmsProductSkuStock record);
}