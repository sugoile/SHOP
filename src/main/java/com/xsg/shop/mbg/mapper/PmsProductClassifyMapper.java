package com.xsg.shop.mbg.mapper;

import com.xsg.shop.mbg.model.PmsProductClassify;
import com.xsg.shop.mbg.model.PmsProductClassifyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PmsProductClassifyMapper {
    long countByExample(PmsProductClassifyExample example);

    int deleteByExample(PmsProductClassifyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PmsProductClassify record);

    int insertSelective(PmsProductClassify record);

    List<PmsProductClassify> selectByExample(PmsProductClassifyExample example);

    PmsProductClassify selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PmsProductClassify record, @Param("example") PmsProductClassifyExample example);

    int updateByExample(@Param("record") PmsProductClassify record, @Param("example") PmsProductClassifyExample example);

    int updateByPrimaryKeySelective(PmsProductClassify record);

    int updateByPrimaryKey(PmsProductClassify record);
}