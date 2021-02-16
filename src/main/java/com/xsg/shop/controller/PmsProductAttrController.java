package com.xsg.shop.controller;

import com.xsg.shop.common.CommonResult;
import com.xsg.shop.dto.PmsProductAttributeparam;
import com.xsg.shop.dto.ShowAttribute;
import com.xsg.shop.service.PmsProductAttrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品动态静态参数管理
 * Created by xsg on 2020/4/13 16:27
 */
@Controller
@Api(tags = "PmsProductAttrController", description = "商品参数管理")
@RequestMapping("/attribute")
public class PmsProductAttrController {
    @Autowired
    private PmsProductAttrService pmsProductAttrService;

    @ApiOperation("添加动态属性参数")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addDynamic(@Validated @RequestBody PmsProductAttributeparam pmsProductAttributeparam, BindingResult result){
        int count = pmsProductAttrService.add(pmsProductAttributeparam);
        if(count > 0){
           return CommonResult.success(count);
        }
        else {
            return CommonResult.failed("添加动态属性列表失败");
        }
    }

    @ApiOperation("获取某一分类下的所有动态参数")
    @RequestMapping(value = "/GetDynamicByClassifyid/{classifyid}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<ShowAttribute>> GetDynamicByClassifyid(@PathVariable(value = "classifyid")Long classifyid){
        return CommonResult.success(pmsProductAttrService.GetDynamicByClassifyid(classifyid));
    }

    @ApiOperation("获取某一分类下的所有静态参数")
    @RequestMapping(value = "/GetStaticByClassifyid/{classifyid}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<ShowAttribute>> GetStaticByClassifyid(@PathVariable(value = "classifyid")Long classifyid){
        return CommonResult.success(pmsProductAttrService.GetStaticByClassifyid(classifyid));
    }

    @ApiOperation("更新属性参数")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable(value = "id")Long id,
                               @Validated @RequestBody PmsProductAttributeparam pmsProductAttributeparam, BindingResult result){
        int count = pmsProductAttrService.update(id, pmsProductAttributeparam);
        if(count > 0){
            return CommonResult.success(count);
        }
        else {
            return CommonResult.failed("更改参数失败");
        }
    }

    @ApiOperation("删除属性参数")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult delete(@PathVariable(value = "id")Long id){
        int count = pmsProductAttrService.delete(id);
        if(count > 0){
            return CommonResult.success(count);
        }
        else {
            return CommonResult.failed("删除参数失败");
        }
    }

    @ApiOperation("删除属性值")
    @RequestMapping(value = "/deleteValue/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult deleteValue(@PathVariable(value = "id")Long id,
                                    @RequestParam(value = "tag")String tag){
        return CommonResult.success(pmsProductAttrService.deleteValue(id, tag));
    }

    @ApiOperation("添加动态属性值")
    @RequestMapping(value = "/addDynamicValue/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addDynamicValue(@PathVariable(value = "id")Long Attrid,
                                    @RequestParam(value = "value")String value){
        return CommonResult.success(pmsProductAttrService.addDynamicValue(Attrid, value));
    }

}
