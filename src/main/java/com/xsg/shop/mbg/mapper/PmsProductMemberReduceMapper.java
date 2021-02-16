package com.xsg.shop.mbg.mapper;

import com.xsg.shop.mbg.model.PmsProductMemberReduce;
import com.xsg.shop.mbg.model.PmsProductMemberReduceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PmsProductMemberReduceMapper {
    long countByExample(PmsProductMemberReduceExample example);

    int deleteByExample(PmsProductMemberReduceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PmsProductMemberReduce record);

    int insertSelective(PmsProductMemberReduce record);

    List<PmsProductMemberReduce> selectByExample(PmsProductMemberReduceExample example);

    PmsProductMemberReduce selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PmsProductMemberReduce record, @Param("example") PmsProductMemberReduceExample example);

    int updateByExample(@Param("record") PmsProductMemberReduce record, @Param("example") PmsProductMemberReduceExample example);

    int updateByPrimaryKeySelective(PmsProductMemberReduce record);

    int updateByPrimaryKey(PmsProductMemberReduce record);
}