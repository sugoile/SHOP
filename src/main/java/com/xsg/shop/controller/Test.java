package com.xsg.shop.controller;


import com.xsg.shop.common.CommonResult;
import com.xsg.shop.mbg.model.PmsProduct;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 测试
 * Created by xsg on 2020/3/17 20:44
 */
@Controller
@Api(tags = "Test",description = "Test")
@RequestMapping("/Test")
public class Test {
    @ApiOperation("测试")
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('pms:product:read')")
    public CommonResult text(){
        return CommonResult.success("success");
    }

}
