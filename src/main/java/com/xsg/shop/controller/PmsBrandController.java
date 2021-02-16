package com.xsg.shop.controller;

import com.xsg.shop.common.CommonPage;
import com.xsg.shop.common.CommonResult;
import com.xsg.shop.dto.PmsProductBrandparam;
import com.xsg.shop.mbg.model.PmsBrand;
import com.xsg.shop.service.PmsBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Created by xsg on 2020/4/15 22:02
 */
@Controller
@Api(tags = "PmsBrandController", description = "商品品牌管理")
@RequestMapping("/Brand")
public class PmsBrandController {
    @Autowired
    private PmsBrandService pmsBrandService;

    @ApiOperation("添加品牌")
    @PostMapping(value = "/add")
    @ResponseBody
    public CommonResult add(@Validated @RequestBody PmsProductBrandparam pmsProductBrandparam, BindingResult result) {
        int count = pmsBrandService.add(pmsProductBrandparam);
        if (count > 0) {
            return CommonResult.success(count);
        } else if (count == -1) {
            return CommonResult.failed("重复命名");
        } else {
            return CommonResult.failed("添加失败");
        }
    }

    @ApiOperation("更新品牌")
    @PostMapping(value = "/update/{id}")
    @ResponseBody
    public CommonResult update(@PathVariable(value = "id")Long id,
                               @Validated @RequestBody PmsProductBrandparam pmsProductBrandparam, BindingResult result) {
        int count = pmsBrandService.update(id, pmsProductBrandparam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed("更新失败");
        }
    }

    @ApiOperation("获取所有品牌不分页不查询")
    @GetMapping(value = "/getBrand")
    @ResponseBody
    public CommonResult<List<PmsBrand>> getBrand() {
        return CommonResult.success(pmsBrandService.getBrand());
    }

    @ApiOperation("获取所有品牌")
    @GetMapping(value = "/getListBrand")
    @ResponseBody
    public CommonResult<CommonPage<PmsBrand>> getListBrand(@RequestParam(value = "query") String query,
                                                           @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        return CommonResult.success(CommonPage.resultPage(pmsBrandService.getListBrand(query, pageSize, pageNum)));
    }

    @ApiOperation("修改开启关闭状态")
    @RequestMapping(value = "/updateShowstatu/{ID}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult updateShowstatu(@PathVariable("ID") Long ID,
                                            @RequestParam("showStatu") Integer showStatu) {
        int count = pmsBrandService.updateShowstatu(ID, showStatu);
        if (count <= 0) {
            return CommonResult.failed("修改失败");
        } else
            return CommonResult.success(count);
    }

    @ApiOperation("删除品牌")
    @RequestMapping(value = "/deleteBrand/{ID}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult DeleteBrand(@PathVariable("ID") Long ID) {
        int count = pmsBrandService.DeleteBrand(ID);
        if (count == -1) {
            return CommonResult.failed("该品牌不存在");
        } else
            return CommonResult.success(count);
    }

    @ApiOperation("通过id返回一个品牌")
    @RequestMapping(value = "/getBrandById/{ID}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult GetBrandById(@PathVariable("ID") Long ID) {
        return CommonResult.success(pmsBrandService.GetBrandById(ID));
    }

    @ApiOperation("删除图片地址")
    @RequestMapping(value = "/deletepic/{ID}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult deletepic(@PathVariable("ID") Long ID) {
        int count = pmsBrandService.deletepic(ID);
        if (count <= 0) {
            return CommonResult.failed("删除图片失败");
        } else
            return CommonResult.success(count);

    }

    @ApiOperation("批量删除品牌")
    @RequestMapping(value = "/deleteList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult deleteList(@RequestParam("ids") List<Long> ids) {
        int count = pmsBrandService.deleteList(ids);
        if (count <= 0) {
            return CommonResult.failed("批量删除品牌失败");
        } else
            return CommonResult.success(count);
    }

    @ApiOperation("批量开启品牌")
    @RequestMapping(value = "/openShowStatuList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult openShowStatuList(@RequestParam("ids") List<Long> ids) {
        int count = pmsBrandService.openShowStatuList(ids);
        if (count <= 0) {
            return CommonResult.failed("批量开启品牌失败");
        } else
            return CommonResult.success(count);
    }

    @ApiOperation("批量关闭品牌")
    @RequestMapping(value = "/closeShowStatuList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult closeShowStatuList(@RequestParam("ids") List<Long> ids) {
        int count = pmsBrandService.closeShowStatuList(ids);
        if (count <= 0) {
            return CommonResult.failed("批量关闭品牌失败");
        } else
            return CommonResult.success(count);
    }
}
