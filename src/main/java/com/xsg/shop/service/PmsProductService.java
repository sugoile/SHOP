package com.xsg.shop.service;

import com.xsg.shop.dto.PmsProductParam;
import com.xsg.shop.dto.PmsProductPromotionParam;
import com.xsg.shop.dto.PmsProductQueryParam;
import com.xsg.shop.mbg.model.*;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品管理service
 * Created by xsg on 2020/2/13 16:04
 */
public interface PmsProductService {

    /**
     * 创建商品
     */
    int create(PmsProductParam pmsProduct);

    /**
     * 搜索商品
     */
    List<PmsProduct> Query(PmsProductQueryParam productQueryParam,Integer PageNum,Integer PageSize);

    /**
     * 批量修改产品的上架状态
     */
    int UpdatePublishStatu(List<Long> ids, Integer publishStatu);

    int UpdateNewStatu(List<Long> ids, Integer newStatu);

    int UpdateRecommandStatu(List<Long> ids, Integer recommandStatu);

    List<PmsProductSkuStock> getSku(Long id);

    int updateSku(List<PmsProductSkuStock> skuStocks);

    int moveBin(Long id);

    PmsProductParam getProductById(Long id);

    List<PmsProductAttributeValue>  getDynamicAttrById(Long id);

    List<PmsProductAttributeValue> getStaticAttrById(Long id);

    PmsProductPromotionParam getPromotionById(Long id);

    Integer getMemberReduceById(Long id);

    List<PmsProductQuantityDiscount> getQuantityDiscountById(Long id);

    List<PmsProductFullReduce> getFullReduceById(Long id);

    int update(Long id, PmsProductParam pmsProductParam);

    List<PmsProduct> BinIndex();

    int deleteProduct(Long id);

    int Movein(Long id);
}
