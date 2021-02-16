package com.xsg.shop.controller;

import com.xsg.shop.common.CommonPage;
import com.xsg.shop.common.CommonResult;
import com.xsg.shop.dto.ClassifyTree;
import com.xsg.shop.dto.PmsProductClassifyParam;
import com.xsg.shop.mbg.model.PmsProductClassify;
import com.xsg.shop.service.PmsProductClassifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品分类管理
 * Created by xsg on 2020/4/7 9:40
 */
@Api(tags = "PmsProductClassifyController", description = "商品类型管理")
@RequestMapping("/ProductClassify")
@Controller
public class PmsProductClassifyController {
    @Autowired
    private PmsProductClassifyService pmsProductClassifyService;

    @ApiOperation("添加商品分类")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@Validated @RequestBody PmsProductClassifyParam pmsProductClassifyParam, BindingResult bindingResult) {
        int count = pmsProductClassifyService.create(pmsProductClassifyParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else if (count == -1) {
            return CommonResult.failed("用户名重复");
        } else {
            return CommonResult.failed("添加失败");
        }
    }

    @ApiOperation("查找所有的0级分类")
    @RequestMapping(value = "/getOnelevelList/{ID}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<PmsProductClassify>> getOnelevelList(@PathVariable(value = "ID")Long id,
                                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer PageNum,
                                                                  @RequestParam(value = "pageSize", defaultValue = "5") Integer PageSize) {
        List<PmsProductClassify> pmsProductClassifies = pmsProductClassifyService.getOnelevelList( PageNum, PageSize, id);
        return CommonResult.success(CommonPage.resultPage(pmsProductClassifies));
    }

    @ApiOperation("通过id返回商品类型树结构")
    @RequestMapping(value = "/GetTreebyClassifyId/{ID}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult GetTreebyClassifyId(@PathVariable("ID") Long ID) {
        return CommonResult.success(pmsProductClassifyService.GetTreebyClassifyId(ID));
    }

    @ApiOperation("删除类型")
    @RequestMapping(value = "/DeleteClassify/{ID}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult DeleteClassify(@PathVariable("ID") Long ID) {
        int count = pmsProductClassifyService.DeleteClassify(ID);
        if(count == -1){
            return CommonResult.failed("该类型不存在");
        }
        else
        return CommonResult.success(count);
    }

    @ApiOperation("修改导航栏开启关闭状态")
    @RequestMapping(value = "/updateNavstatu/{ID}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult updateNavstatu(@PathVariable("ID") Long ID,
                                       @RequestParam("navStatu")Integer navStatu ) {
        int count = pmsProductClassifyService.updateNavstatu(ID, navStatu);
        if(count <= 0){
            return CommonResult.failed("修改失败");
        }
        else
            return CommonResult.success(count);
    }

    @ApiOperation("修改开启关闭状态")
    @RequestMapping(value = "/updateShowstatu/{ID}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult updateShowstatu(@PathVariable("ID") Long ID,
                                       @RequestParam("showStatu")Integer showStatu ) {
        int count = pmsProductClassifyService.updateShowstatu(ID, showStatu);
        if(count <= 0){
            return CommonResult.failed("修改失败");
        }
        else
            return CommonResult.success(count);
    }


    @ApiOperation("通过id返回一个类型")
    @RequestMapping(value = "/GetClassifyById/{ID}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult GetClassifyById(@PathVariable("ID") Long ID) {
        return CommonResult.success(pmsProductClassifyService.GetClassifyById(ID));
    }

    @ApiOperation("返回商品类型一二级树结构")
    @RequestMapping(value = "/GetTree", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<ClassifyTree>> GetTree() {
        return CommonResult.success(pmsProductClassifyService.GetTree());
    }

    @ApiOperation("更新商品分类")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable(value = "id")Long Classifyid,
                               @Validated @RequestBody PmsProductClassifyParam pmsProductClassifyParam, BindingResult bindingResult) {
        int count = pmsProductClassifyService.update(Classifyid, pmsProductClassifyParam);
        if (count == 1) {
            return CommonResult.success(count);
        }
        else if(count == 2){
            return CommonResult.failed("不能设置上级分类为本分类");
        }
        else {
            return CommonResult.failed("更新失败");
        }
    }

    @ApiOperation("通过id返回父级id")
    @RequestMapping(value = "/GetParentById/{ID}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult GetParentById(@PathVariable("ID") Long ID) {
        return CommonResult.success(pmsProductClassifyService.GetParentById(ID));
    }

    @ApiOperation("返回所有")
    @RequestMapping(value = "/ClassifyCascaderTree", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult ClassifyCascaderTree() {
        return CommonResult.success(pmsProductClassifyService.ClassifyCascaderTree());
    }

    @ApiOperation("查找所有的3级分类")
    @RequestMapping(value = "/getThreelevelList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<PmsProductClassify>> getThreelevelList() {
        return CommonResult.success(pmsProductClassifyService.getThreelevelList());
    }
}

