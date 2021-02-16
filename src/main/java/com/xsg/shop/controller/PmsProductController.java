package com.xsg.shop.controller;

import com.xsg.shop.common.CommonPage;
import com.xsg.shop.common.CommonResult;
import com.xsg.shop.dto.PmsProductParam;
import com.xsg.shop.dto.PmsProductQueryParam;
import com.xsg.shop.mbg.model.PmsProduct;
import com.xsg.shop.mbg.model.PmsProductSkuStock;
import com.xsg.shop.service.PmsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品管理Controller
 * Created by xsg on 2020/2/13 15:59
 */
@Controller
@Api(tags = "PmsProductController", description = "商品管理")
@RequestMapping("/Product")
public class PmsProductController {

    @Autowired
    private PmsProductService productService;

    @ApiOperation("创建商品")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody PmsProductParam product) {
        int count = productService.create(product);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("查询产品")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<PmsProduct>> Query(PmsProductQueryParam productQueryParam,
                                                      @RequestParam(value = "PageSize", defaultValue = "5") Integer PageSize,
                                                      @RequestParam(value = "PageNum", defaultValue = "1") Integer PageNum
    ) {
        List<PmsProduct> pmsProducts = productService.Query(productQueryParam, PageNum, PageSize);
        return CommonResult.success(CommonPage.resultPage(pmsProducts));
    }

    @ApiOperation("更新产品的上架状态")
    @RequestMapping(value = "/update/PublishStatu", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult UpdateVerifyStatus(@RequestParam("ids") List<Long> ids, @RequestParam("publishStatu") Integer publishStatu) {
        int count = productService.UpdatePublishStatu(ids, publishStatu);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("更新产品的新品状态")
    @RequestMapping(value = "/update/NewStatu", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult UpdateNewStatu(@RequestParam("ids") List<Long> ids, @RequestParam("newStatu") Integer newStatu) {
        int count = productService.UpdateNewStatu(ids, newStatu);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("更新产品的推荐状态")
    @RequestMapping(value = "/update/RecommandStatu", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult UpdateRecommandStatu(@RequestParam("ids") List<Long> ids, @RequestParam("recommandStatu") Integer recommandStatu) {
        int count = productService.UpdateRecommandStatu(ids, recommandStatu);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("获取库存状态")
    @RequestMapping(value = "/getSku/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<PmsProductSkuStock>> getSku(@PathVariable(value = "id") Long id) {
        return CommonResult.success(productService.getSku(id));
    }

    @ApiOperation("更新库存状态")
    @RequestMapping(value = "/updateSku", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateSku(@RequestBody List<PmsProductSkuStock> skuStocks) {
        int count = productService.updateSku(skuStocks);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("商品移入回收站")
    @RequestMapping(value = "/moveBin/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult moveBin(@PathVariable(value = "id")Long id) {
        int count = productService.moveBin(id);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("通过id找产品")
    @RequestMapping(value = "/getProductById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getProductById(@PathVariable(value = "id")Long id) {
        return CommonResult.success(productService.getProductById(id));
    }

    @ApiOperation("通过id找产品的动态数值")
    @RequestMapping(value = "/getDynamicAttrById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getDynamicAttrById(@PathVariable(value = "id")Long id) {
        return CommonResult.success(productService.getDynamicAttrById(id));
    }

    @ApiOperation("通过id找产品的动态数值")
    @RequestMapping(value = "/getStaticAttrById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getStaticAttrById(@PathVariable(value = "id")Long id) {
        return CommonResult.success(productService.getStaticAttrById(id));
    }

    @ApiOperation("通过id找产品的促销优惠")
    @RequestMapping(value = "/getPromotionById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getPromotionById(@PathVariable(value = "id")Long id) {
        return CommonResult.success(productService.getPromotionById(id));
    }

    @ApiOperation("通过id找产品的会员优惠")
    @RequestMapping(value = "/getMemberReduceById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getMemberReduceById(@PathVariable(value = "id")Long id) {
        return CommonResult.success(productService.getMemberReduceById(id));
    }

    @ApiOperation("通过id找产品的阶梯优惠")
    @RequestMapping(value = "/getQuantityDiscountById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getQuantityDiscountById(@PathVariable(value = "id")Long id) {
        return CommonResult.success(productService.getQuantityDiscountById(id));
    }

    @ApiOperation("通过id找产品的满减优惠")
    @RequestMapping(value = "/getFullReduceById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getFullReduceById(@PathVariable(value = "id")Long id) {
        return CommonResult.success(productService.getFullReduceById(id));
    }

    @ApiOperation("更新产品")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable(value = "id")Long id, @RequestBody PmsProductParam pmsProductParam) {
        int count = productService.update(id, pmsProductParam);
        if(count > 0){
            return CommonResult.success(count);
        }else{
            return CommonResult.failed("后端问题...");
        }
    }

    @ApiOperation("回收站的商品")
    @RequestMapping(value = "/BinIndex", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult BinIndex() {
        return CommonResult.success(productService.BinIndex());
    }

    @ApiOperation("删除商品")
    @RequestMapping(value = "/deleteProduct/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult deleteProduct(@PathVariable(value = "id")Long id) {
        int count = productService.deleteProduct(id);
        if(count > 0) {
            return CommonResult.success(count);
        }else {
            return CommonResult.failed("移动失败");
        }
    }

    @ApiOperation("移回商品")
    @RequestMapping(value = "/Movein/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult Movein(@PathVariable(value = "id")Long id) {
        return CommonResult.success(productService.Movein(id));
    }

}
