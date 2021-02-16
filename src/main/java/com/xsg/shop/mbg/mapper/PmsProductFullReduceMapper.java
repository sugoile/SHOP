package com.xsg.shop.mbg.mapper;

import com.xsg.shop.mbg.model.PmsProductFullReduce;
import com.xsg.shop.mbg.model.PmsProductFullReduceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PmsProductFullReduceMapper {
    long countByExample(PmsProductFullReduceExample example);

    int deleteByExample(PmsProductFullReduceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PmsProductFullReduce record);

    int insertSelective(PmsProductFullReduce record);

    List<PmsProductFullReduce> selectByExample(PmsProductFullReduceExample example);

    PmsProductFullReduce selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PmsProductFullReduce record, @Param("example") PmsProductFullReduceExample example);

    int updateByExample(@Param("record") PmsProductFullReduce record, @Param("example") PmsProductFullReduceExample example);

    int updateByPrimaryKeySelective(PmsProductFullReduce record);

    int updateByPrimaryKey(PmsProductFullReduce record);
}