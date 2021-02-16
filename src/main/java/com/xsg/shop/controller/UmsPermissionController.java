package com.xsg.shop.controller;

import com.xsg.shop.common.CommonResult;
import com.xsg.shop.dto.PermissionTree;
import com.xsg.shop.dto.UmsPermissionParam;
import com.xsg.shop.mbg.model.UmsPermission;
import com.xsg.shop.service.UmsPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限管理controller
 * Created by xsg on 2020/3/26 19:19
 */
@Controller
@Api(tags = "UmsPermissionController", description = "权限管理")
@RequestMapping("/umspermission")
public class UmsPermissionController {

    @Autowired
    private UmsPermissionService umsPermissionService;

    @ApiOperation(value = "添加权限")
    @PostMapping(value = "/addpermission")
    @ResponseBody
    public CommonResult addpermission(@Validated  @RequestBody UmsPermissionParam umsPermissionParam, BindingResult result) {
        UmsPermission umsPermission = umsPermissionService.addpermission(umsPermissionParam);
        if(umsPermission!=null){
            return CommonResult.success(umsPermission);
        }
        else{
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "获取所有权限")
    @GetMapping(value = "/getList")
    @ResponseBody
    public CommonResult<List<UmsPermission>> getList() {
        List<UmsPermission> umsPermissions = umsPermissionService.getList();
        return CommonResult.success(umsPermissions);
    }


    @ApiOperation(value = "获取所有权限成树状")
    @GetMapping(value = "/getTree")
    @ResponseBody
    public CommonResult<List<PermissionTree>> getTree() {
        List<PermissionTree> permissionTrees = umsPermissionService.getTree();
        return CommonResult.success(permissionTrees);
    }

    @ApiOperation(value = "通过角色ID获取所有权限树结构")
    @GetMapping(value = "/getTreeByRoleID/{roleid}")
    @ResponseBody
    public CommonResult<List<PermissionTree>> getTreeByRoleID(@PathVariable(value = "roleid")Long roleid) {
        List<PermissionTree> umsPermissions = umsPermissionService.getTreeByRoleID(roleid);
        return CommonResult.success(umsPermissions);
    }


}
