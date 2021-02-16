package com.xsg.shop.service.Imp;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.xsg.shop.dto.*;
import com.xsg.shop.mbg.mapper.*;
import com.xsg.shop.mbg.model.*;
import com.xsg.shop.service.PmsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 商品管理的实现类
 * Created by xsg on 2020/2/13 16:28
 */
@Service
public class PmsProductServiceImp implements PmsProductService {

    @Autowired
    private PmsProductMapper pmsproductmapper;
    @Autowired
    private PmsProductAttributeValueMapper pmsProductAttributeValueMapper;
    @Autowired
    private PmsProductSkuStockMapper pmsProductSkuStockMapper;
    @Autowired
    private PmsProductPromotionPriceMapper pmsProductPromotionPriceMapper;
    @Autowired
    private PmsProductMemberReduceMapper pmsProductMemberReduceMapper;
    @Autowired
    private PmsProductQuantityDiscountMapper pmsProductQuantityDiscountMapper;
    @Autowired
    private PmsProductFullReduceMapper pmsProductFullReduceMapper;

    @Override
    public int create(PmsProductParam pmsProduct){
        PmsProduct product = pmsProduct;
        product.setId(null);
        product.setMoveBin(0);
        //设置审核状态需要通过审核后台(这里默认审核通过)
        product.setVerifyStatus(1);
        product.setSale(0);
        if (product.getProductNumber() == null) {
            String result = "";
            Random random = new Random();
            for (int i = 0; i < 6; i++) {
                result += random.nextInt(10);
            }
            product.setProductNumber(Long.parseLong(result));
        }
        pmsproductmapper.insertSelective(product);
        //这里设置商品的一些规格参数绑定
        PmsProductExample pmsProductExample = new PmsProductExample();
        pmsProductExample.createCriteria().andNameEqualTo(product.getName()).andSubTitleEqualTo(product.getSubTitle()).andProductNumberEqualTo(product.getProductNumber());
        Long productid = pmsproductmapper.selectByExample(pmsProductExample).get(0).getId();
        List<DynamicAttr> dynamicAttrs = pmsProduct.getProductDynamicAttrList();
        for (DynamicAttr dynamicAttr : dynamicAttrs) {
            PmsProductAttributeValue pmsProductAttributeValue = new PmsProductAttributeValue();
            pmsProductAttributeValue.setId(null);
            pmsProductAttributeValue.setProductId(productid);
            pmsProductAttributeValue.setProductAttrId(dynamicAttr.getId());
            pmsProductAttributeValue.setType(1);
            String dynamicAttrvalues = null;
            for (String dynamicAttrvalue : dynamicAttr.getValues()) {
                if (dynamicAttrvalues == null) {
                    dynamicAttrvalues = dynamicAttrvalue;
                } else {
                    dynamicAttrvalues += "," + dynamicAttrvalue;
                }
            }
            pmsProductAttributeValue.setValue(dynamicAttrvalues);
            pmsProductAttributeValueMapper.insertSelective(pmsProductAttributeValue);
        }
        List<StaticAttr> staticAttrs = pmsProduct.getProductStaticAttrList();
        for (StaticAttr staticAttr : staticAttrs) {
            PmsProductAttributeValue pmsProductAttributeValue = new PmsProductAttributeValue();
            pmsProductAttributeValue.setId(null);
            pmsProductAttributeValue.setType(0);
            pmsProductAttributeValue.setValue(staticAttr.getValue());
            pmsProductAttributeValue.setProductId(productid);
            pmsProductAttributeValue.setProductAttrId(staticAttr.getId());
            pmsProductAttributeValueMapper.insertSelective(pmsProductAttributeValue);
        }
        //设置库存信息
        if (pmsProduct.getSku().size() != 0) {
            List<PmsProductSkuStock> pmsProductSkuStocks = pmsProduct.getSku();
            for (PmsProductSkuStock pmsProductSkuStock : pmsProductSkuStocks) {
                pmsProductSkuStock.setId(null);
                pmsProductSkuStock.setProductId(productid);
                pmsProductSkuStock.setSale(0);
                pmsProductSkuStockMapper.insertSelective(pmsProductSkuStock);
            }
        }
        String DiscountType = null;
        //设置促销优惠信息
        PmsProductPromotionParam productPromotionPrice = pmsProduct.getProductPromotionPrice();
        if (productPromotionPrice.getPromotionStart() != null && productPromotionPrice.getPromotionEnd() != null
                && productPromotionPrice.getPromotionPrice().compareTo(BigDecimal.ZERO) != 0) {
            PmsProductPromotionPrice pmsProductPromotionPrice = new PmsProductPromotionPrice();
            SimpleDateFormat simpleDateFormat =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
            try {
                Date promotionStart = simpleDateFormat.parse(productPromotionPrice.getPromotionStart());
                Date promotionEnd  = simpleDateFormat.parse(productPromotionPrice.getPromotionEnd());
                pmsProductPromotionPrice.setId(null);
                pmsProductPromotionPrice.setProductId(productid);
                pmsProductPromotionPrice.setPromotionPrice(productPromotionPrice.getPromotionPrice());
                pmsProductPromotionPrice.setPromotionStart(promotionStart);
                pmsProductPromotionPrice.setPromotionEnd(promotionEnd);
                pmsProductPromotionPriceMapper.insertSelective(pmsProductPromotionPrice);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DiscountType = "1";
        }
        //设置会员优惠信息
        if (pmsProduct.getProductMemberReduce() != 0) {
            PmsProductMemberReduce pmsProductMemberReduce = new PmsProductMemberReduce();
            pmsProductMemberReduce.setId(null);
            pmsProductMemberReduce.setProductId(productid);
            pmsProductMemberReduce.setPlusMemberReduce(pmsProduct.getProductMemberReduce());
            pmsProductMemberReduceMapper.insertSelective(pmsProductMemberReduce);
            if(DiscountType == null){
                DiscountType = "2";
            }else {
                DiscountType += ",2";
            }
        }
        //设置阶梯优惠信息
        List<PmsProductQuantityDiscount> productQuantityDiscount = pmsProduct.getProductQuantityDiscount();
        if (productQuantityDiscount.size() > 1 || ((productQuantityDiscount.size() == 1) && productQuantityDiscount.get(0).getCount() != 0 && productQuantityDiscount.get(0).getQuantityDiscount().compareTo(BigDecimal.ZERO) != 0)){
            for(PmsProductQuantityDiscount quantityDiscount: productQuantityDiscount){
                if(quantityDiscount.getCount()!=0){
                    quantityDiscount.setProductId(productid);
                    pmsProductQuantityDiscountMapper.insertSelective(quantityDiscount);
                }
            }
            if(DiscountType == null){
                DiscountType = "3";
            }else {
                DiscountType += ",3";
            }
        }
        //设置满减优惠信息
        List<PmsProductFullReduce> pmsProductFullReduces = pmsProduct.getProductFullReduce();
        if (pmsProductFullReduces.size() > 1 || ((pmsProductFullReduces.size() == 1) && pmsProductFullReduces.get(0).getFullPrice() != 0 && pmsProductFullReduces.get(0).getFullReduce() != 0)){
            for(PmsProductFullReduce productFullReduce: pmsProductFullReduces){
                if(productFullReduce.getFullReduce()!=0){
                    productFullReduce.setProductId(productid);
                    pmsProductFullReduceMapper.insertSelective(productFullReduce);
                }
            }
            if(DiscountType == null){
                DiscountType = "4";
            }else {
                DiscountType += ",4";
            }
        }

        if(DiscountType == null) DiscountType = "0";
        product.setDiscountType(DiscountType);
        int count = pmsproductmapper.updateByPrimaryKeySelective(product);
            return count;
    }

    @Override
    public List<PmsProduct> Query(PmsProductQueryParam productQueryParam, Integer PageNum, Integer PageSize) {
        //开始分页
        PageHelper.startPage(PageNum, PageSize);
        //该类有许多复杂的查询方法和and or查询
        PmsProductExample pmsProductExample = new PmsProductExample();
        PmsProductExample.Criteria criteria = pmsProductExample.createCriteria().andMoveBinEqualTo(0);
        if (!StringUtils.isEmpty(productQueryParam.getKeyword()) && productQueryParam.getKeyword().length() > 0) {
            criteria.andKeywordsLike("%" + productQueryParam.getKeyword() + "%");
        }
        if (productQueryParam.getBrandId() != null) {
            criteria.andBrandIdEqualTo(productQueryParam.getBrandId());
        }
        if (productQueryParam.getClassifyId() != null) {
            criteria.andClassifyIdEqualTo(productQueryParam.getClassifyId());
        }
        if (productQueryParam.getVerifyStatus() != null) {
            criteria.andVerifyStatusEqualTo(productQueryParam.getVerifyStatus());
        }
        if (productQueryParam.getPublishStatus() != null) {
            criteria.andPublishStatuEqualTo(productQueryParam.getPublishStatus());
        }
        if (!StringUtils.isEmpty(productQueryParam.getProductSn()) && productQueryParam.getProductSn().length()>0) {
            criteria.andNameLike("%" + productQueryParam.getProductSn() + "%");
        }
        return pmsproductmapper.selectByExample(pmsProductExample);
    }

    @Override
    public int UpdatePublishStatu(List<Long> ids, Integer publishStatu) {
        int count = 0;
        PmsProduct product = new PmsProduct();
        product.setPublishStatu(publishStatu);
        PmsProductExample pmsProductExample = new PmsProductExample();
        pmsProductExample.createCriteria().andIdIn(ids);
        count = pmsproductmapper.updateByExampleSelective(product, pmsProductExample);
        return count;
    }

    @Override
    public int UpdateNewStatu(List<Long> ids, Integer newStatu) {
        int count = 0;
        PmsProduct product = new PmsProduct();
        product.setNewStatus(newStatu);
        PmsProductExample pmsProductExample = new PmsProductExample();
        pmsProductExample.createCriteria().andIdIn(ids);
        count = pmsproductmapper.updateByExampleSelective(product, pmsProductExample);
        return count;
    }

    @Override
    public int UpdateRecommandStatu(List<Long> ids, Integer recommandStatu) {
        int count = 0;
        PmsProduct product = new PmsProduct();
        product.setRecommandStatus(recommandStatu);
        PmsProductExample pmsProductExample = new PmsProductExample();
        pmsProductExample.createCriteria().andIdIn(ids);
        count = pmsproductmapper.updateByExampleSelective(product, pmsProductExample);
        return count;
    }

    @Override
    public List<PmsProductSkuStock> getSku(Long id) {
        PmsProductSkuStockExample pmsProductSkuStockExample = new PmsProductSkuStockExample();
        pmsProductSkuStockExample.createCriteria().andProductIdEqualTo(id);
        return pmsProductSkuStockMapper.selectByExample(pmsProductSkuStockExample);
    }

    @Override
    public int updateSku(List<PmsProductSkuStock> skuStocks) {
        int count = 0;
        for(PmsProductSkuStock skuStock : skuStocks){
            pmsProductSkuStockMapper.updateByPrimaryKey(skuStock);
            count++;
        }
        return count;
    }

    @Override
    public int moveBin(Long id) {
       PmsProduct product =  pmsproductmapper.selectByPrimaryKey(id);
        product.setMoveBin(1);
        return pmsproductmapper.updateByPrimaryKey(product);
    }

    @Override
    public PmsProductParam getProductById(Long id) {
        PmsProduct product = pmsproductmapper.selectByPrimaryKey(id);
        PmsProductParam pmsProductParam = new PmsProductParam();
        BeanUtil.copyProperties(product, pmsProductParam);
        pmsProductParam.setProductDynamicAttrList(new ArrayList<>());
        pmsProductParam.setProductStaticAttrList(new ArrayList<>());
        return pmsProductParam;
    }

    @Override
    public List<PmsProductAttributeValue> getDynamicAttrById(Long id) {
        PmsProductAttributeValueExample pmsProductAttributeValueExample = new PmsProductAttributeValueExample();
        pmsProductAttributeValueExample.createCriteria().andProductIdEqualTo(id).andTypeEqualTo(1);
        return pmsProductAttributeValueMapper.selectByExample(pmsProductAttributeValueExample);
    }

    @Override
    public List<PmsProductAttributeValue> getStaticAttrById(Long id) {
        PmsProductAttributeValueExample pmsProductAttributeValueExample = new PmsProductAttributeValueExample();
        pmsProductAttributeValueExample.createCriteria().andProductIdEqualTo(id).andTypeEqualTo(0);
        return pmsProductAttributeValueMapper.selectByExample(pmsProductAttributeValueExample);
    }

    @Override
    public PmsProductPromotionParam getPromotionById(Long id) {
        PmsProductPromotionPriceExample pmsProductPromotionPriceExample = new PmsProductPromotionPriceExample();
        pmsProductPromotionPriceExample.createCriteria().andProductIdEqualTo(id);
        List<PmsProductPromotionPrice>pmsProductPromotionPrices =  pmsProductPromotionPriceMapper.selectByExample(pmsProductPromotionPriceExample);
        PmsProductPromotionParam pmsProductPromotionParam = new PmsProductPromotionParam();
        if(pmsProductPromotionPrices.size() > 0){
            BeanUtil.copyProperties(pmsProductPromotionPrices.get(0), pmsProductPromotionParam);
        }
        return pmsProductPromotionParam;
    }

    @Override
    public Integer getMemberReduceById(Long id) {
        PmsProductMemberReduceExample pmsProductMemberReduceExample = new PmsProductMemberReduceExample();
        pmsProductMemberReduceExample.createCriteria().andProductIdEqualTo(id);
        List<PmsProductMemberReduce> pmsProductMemberReduces = pmsProductMemberReduceMapper.selectByExample(pmsProductMemberReduceExample);
        if(pmsProductMemberReduces.size() > 0){
            return pmsProductMemberReduces.get(0).getPlusMemberReduce();
        }else{
            return null;
        }
    }

    @Override
    public List<PmsProductQuantityDiscount> getQuantityDiscountById(Long id) {
        PmsProductQuantityDiscountExample pmsProductQuantityDiscountExample = new PmsProductQuantityDiscountExample();
        pmsProductQuantityDiscountExample.createCriteria().andProductIdEqualTo(id);
        return pmsProductQuantityDiscountMapper.selectByExample(pmsProductQuantityDiscountExample);
    }

    @Override
    public List<PmsProductFullReduce> getFullReduceById(Long id) {
        PmsProductFullReduceExample pmsProductFullReduceExample = new PmsProductFullReduceExample();
        pmsProductFullReduceExample.createCriteria().andProductIdEqualTo(id);
        return pmsProductFullReduceMapper.selectByExample(pmsProductFullReduceExample);
    }

    @Override
    public int update(Long id, PmsProductParam pmsProductParam) {
        PmsProduct product = new PmsProduct();
        BeanUtil.copyProperties(pmsProductParam, product);
        pmsproductmapper.updateByPrimaryKeySelective(product);
        //这里设置商品的一些规格参数绑定
        PmsProductAttributeValueExample pmsProductAttributeValueExample = new PmsProductAttributeValueExample();
        pmsProductAttributeValueExample.createCriteria().andProductIdEqualTo(id);
        pmsProductAttributeValueMapper.deleteByExample(pmsProductAttributeValueExample);
        List<DynamicAttr> dynamicAttrs = pmsProductParam.getProductDynamicAttrList();
        for (DynamicAttr dynamicAttr : dynamicAttrs) {
            PmsProductAttributeValue pmsProductAttributeValue = new PmsProductAttributeValue();
            pmsProductAttributeValue.setId(null);
            pmsProductAttributeValue.setProductId(id);
            pmsProductAttributeValue.setProductAttrId(dynamicAttr.getId());
            pmsProductAttributeValue.setType(1);
            String dynamicAttrvalues = null;
            for (String dynamicAttrvalue : dynamicAttr.getValues()) {
                if (dynamicAttrvalues == null) {
                    dynamicAttrvalues = dynamicAttrvalue;
                } else {
                    dynamicAttrvalues += "," + dynamicAttrvalue;
                }
            }
            pmsProductAttributeValue.setValue(dynamicAttrvalues);
            pmsProductAttributeValueMapper.insertSelective(pmsProductAttributeValue);
        }
        List<StaticAttr> staticAttrs = pmsProductParam.getProductStaticAttrList();
        for (StaticAttr staticAttr : staticAttrs) {
            PmsProductAttributeValue pmsProductAttributeValue = new PmsProductAttributeValue();
            pmsProductAttributeValue.setId(null);
            pmsProductAttributeValue.setType(0);
            pmsProductAttributeValue.setValue(staticAttr.getValue());
            pmsProductAttributeValue.setProductId(id);
            pmsProductAttributeValue.setProductAttrId(staticAttr.getId());
            pmsProductAttributeValueMapper.insertSelective(pmsProductAttributeValue);
        }

        //设置库存信息
        PmsProductSkuStockExample pmsProductSkuStockExample = new PmsProductSkuStockExample();
        pmsProductSkuStockExample.createCriteria().andProductIdEqualTo(id);
        List<PmsProductSkuStock> stocks = pmsProductSkuStockMapper.selectByExample(pmsProductSkuStockExample);
        pmsProductSkuStockMapper.deleteByExample(pmsProductSkuStockExample);
        if (pmsProductParam.getSku().size() != 0) {
            List<PmsProductSkuStock> pmsProductSkuStocks = pmsProductParam.getSku();
            for (PmsProductSkuStock pmsProductSkuStock : pmsProductSkuStocks) {
                pmsProductSkuStock.setId(null);
                pmsProductSkuStock.setProductId(id);
                for(PmsProductSkuStock stock: stocks){
                    if(pmsProductSkuStock.getValue().equals(stock.getValue())){
                        pmsProductSkuStock.setSale(stock.getSale());
                    }
                }
                pmsProductSkuStockMapper.insertSelective(pmsProductSkuStock);
            }
        }

        String DiscountType = null;
        //设置促销优惠信息
        PmsProductPromotionPriceExample pmsProductPromotionPriceExample = new PmsProductPromotionPriceExample();
        pmsProductPromotionPriceExample.createCriteria().andProductIdEqualTo(id);
        pmsProductPromotionPriceMapper.deleteByExample(pmsProductPromotionPriceExample);
        PmsProductPromotionParam productPromotionPrice = pmsProductParam.getProductPromotionPrice();
        if (productPromotionPrice.getPromotionStart() != null && productPromotionPrice.getPromotionEnd() != null
                && productPromotionPrice.getPromotionPrice().compareTo(BigDecimal.ZERO) != 0) {
            PmsProductPromotionPrice pmsProductPromotionPrice = new PmsProductPromotionPrice();
            SimpleDateFormat simpleDateFormat =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
            try {
                Date promotionStart = simpleDateFormat.parse(productPromotionPrice.getPromotionStart());
                Date promotionEnd  = simpleDateFormat.parse(productPromotionPrice.getPromotionEnd());
                pmsProductPromotionPrice.setId(null);
                pmsProductPromotionPrice.setProductId(id);
                pmsProductPromotionPrice.setPromotionPrice(productPromotionPrice.getPromotionPrice());
                pmsProductPromotionPrice.setPromotionStart(promotionStart);
                pmsProductPromotionPrice.setPromotionEnd(promotionEnd);
                pmsProductPromotionPriceMapper.insertSelective(pmsProductPromotionPrice);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DiscountType = "1";
        }
        //设置会员优惠信息
        PmsProductMemberReduceExample pmsProductMemberReduceExample = new PmsProductMemberReduceExample();
        pmsProductMemberReduceExample.createCriteria().andProductIdEqualTo(id);
        pmsProductMemberReduceMapper.deleteByExample(pmsProductMemberReduceExample);
        if (pmsProductParam.getProductMemberReduce() != 0) {
            PmsProductMemberReduce pmsProductMemberReduce = new PmsProductMemberReduce();
            pmsProductMemberReduce.setId(null);
            pmsProductMemberReduce.setProductId(id);
            pmsProductMemberReduce.setPlusMemberReduce(pmsProductParam.getProductMemberReduce());
            pmsProductMemberReduceMapper.insertSelective(pmsProductMemberReduce);
            if(DiscountType == null){
                DiscountType = "2";
            }else {
                DiscountType += ",2";
            }
        }
        //设置阶梯优惠信息
        PmsProductQuantityDiscountExample pmsProductQuantityDiscountExample = new PmsProductQuantityDiscountExample();
        pmsProductQuantityDiscountExample.createCriteria().andProductIdEqualTo(id);
        pmsProductQuantityDiscountMapper.deleteByExample(pmsProductQuantityDiscountExample);
        List<PmsProductQuantityDiscount> productQuantityDiscount = pmsProductParam.getProductQuantityDiscount();
        if (productQuantityDiscount.size() > 1 || ((productQuantityDiscount.size() == 1) && productQuantityDiscount.get(0).getCount() != 0 && productQuantityDiscount.get(0).getQuantityDiscount().compareTo(BigDecimal.ZERO) != 0)){
            for(PmsProductQuantityDiscount quantityDiscount: productQuantityDiscount){
                if(quantityDiscount.getCount()!=0){
                    quantityDiscount.setProductId(id);
                    pmsProductQuantityDiscountMapper.insertSelective(quantityDiscount);
                }
            }
            if(DiscountType == null){
                DiscountType = "3";
            }else {
                DiscountType += ",3";
            }
        }
        //设置满减优惠信息
        PmsProductFullReduceExample pmsProductFullReduceExample = new PmsProductFullReduceExample();
        pmsProductFullReduceExample.createCriteria().andProductIdEqualTo(id);
        pmsProductFullReduceMapper.deleteByExample(pmsProductFullReduceExample);
        List<PmsProductFullReduce> pmsProductFullReduces = pmsProductParam.getProductFullReduce();
        if (pmsProductFullReduces.size() > 1 || ((pmsProductFullReduces.size() == 1) && pmsProductFullReduces.get(0).getFullPrice() != 0 && pmsProductFullReduces.get(0).getFullReduce() != 0)){
            for(PmsProductFullReduce productFullReduce: pmsProductFullReduces){
                if(productFullReduce.getFullReduce()!=0){
                    productFullReduce.setProductId(id);
                    pmsProductFullReduceMapper.insertSelective(productFullReduce);
                }
            }
            if(DiscountType == null){
                DiscountType = "4";
            }else {
                DiscountType += ",4";
            }
        }

        if(DiscountType == null) DiscountType = "0";
        product.setDiscountType(DiscountType);
        int count = pmsproductmapper.updateByPrimaryKeySelective(product);
        return count;
    }

    @Override
    public List<PmsProduct> BinIndex() {
        PmsProductExample pmsProductExample = new PmsProductExample();
        pmsProductExample.createCriteria().andMoveBinEqualTo(1);
        return pmsproductmapper.selectByExample(pmsProductExample);
    }

    @Override
    public int deleteProduct(Long id) {
        PmsProductAttributeValueExample pmsProductAttributeValueExample = new PmsProductAttributeValueExample();
        pmsProductAttributeValueExample.createCriteria().andProductIdEqualTo(id);
        pmsProductAttributeValueMapper.deleteByExample(pmsProductAttributeValueExample);

        PmsProductSkuStockExample pmsProductSkuStockExample = new PmsProductSkuStockExample();
        pmsProductSkuStockExample.createCriteria().andProductIdEqualTo(id);
        pmsProductSkuStockMapper.deleteByExample(pmsProductSkuStockExample);

        PmsProductPromotionPriceExample pmsProductPromotionPriceExample = new PmsProductPromotionPriceExample();
        pmsProductPromotionPriceExample.createCriteria().andProductIdEqualTo(id);
        pmsProductPromotionPriceMapper.deleteByExample(pmsProductPromotionPriceExample);

        PmsProductMemberReduceExample pmsProductMemberReduceExample = new PmsProductMemberReduceExample();
        pmsProductMemberReduceExample.createCriteria().andProductIdEqualTo(id);
        pmsProductMemberReduceMapper.deleteByExample(pmsProductMemberReduceExample);

        PmsProductQuantityDiscountExample pmsProductQuantityDiscountExample = new PmsProductQuantityDiscountExample();
        pmsProductQuantityDiscountExample.createCriteria().andProductIdEqualTo(id);
        pmsProductQuantityDiscountMapper.deleteByExample(pmsProductQuantityDiscountExample);

        PmsProductFullReduceExample pmsProductFullReduceExample = new PmsProductFullReduceExample();
        pmsProductFullReduceExample.createCriteria().andProductIdEqualTo(id);
        pmsProductFullReduceMapper.deleteByExample(pmsProductFullReduceExample);
        return pmsproductmapper.deleteByPrimaryKey(id);
    }

    @Override
    public int Movein(Long id) {
        PmsProduct product =  pmsproductmapper.selectByPrimaryKey(id);
        product.setMoveBin(0);
        return pmsproductmapper.updateByPrimaryKey(product);
    }

}
