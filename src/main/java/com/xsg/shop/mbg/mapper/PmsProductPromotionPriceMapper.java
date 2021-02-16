package com.xsg.shop.mbg.mapper;

import com.xsg.shop.mbg.model.PmsProductPromotionPrice;
import com.xsg.shop.mbg.model.PmsProductPromotionPriceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PmsProductPromotionPriceMapper {
    long countByExample(PmsProductPromotionPriceExample example);

    int deleteByExample(PmsProductPromotionPriceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PmsProductPromotionPrice record);

    int insertSelective(PmsProductPromotionPrice record);

    List<PmsProductPromotionPrice> selectByExample(PmsProductPromotionPriceExample example);

    PmsProductPromotionPrice selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PmsProductPromotionPrice record, @Param("example") PmsProductPromotionPriceExample example);

    int updateByExample(@Param("record") PmsProductPromotionPrice record, @Param("example") PmsProductPromotionPriceExample example);

    int updateByPrimaryKeySelective(PmsProductPromotionPrice record);

    int updateByPrimaryKey(PmsProductPromotionPrice record);
}