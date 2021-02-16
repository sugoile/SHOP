package com.xsg.shop.controller;

import com.xsg.shop.common.CommonPage;
import com.xsg.shop.common.CommonResult;
import com.xsg.shop.dto.RoleTree;
import com.xsg.shop.dto.UmsRoleParam;
import com.xsg.shop.mbg.model.UmsRole;
import com.xsg.shop.service.UmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by xsg on 2020/3/26 15:57
 */
@RequestMapping("/umsRole")
@Controller
@Api(tags = "UmsRoleController", description = "角色管理")
public class UmsRoleController {

    @Autowired
    private UmsRoleService umsRoleService;

    @ApiOperation(value = "添加角色")
    @PostMapping(value = "/addrole")
    @ResponseBody
    public CommonResult register(@Validated @RequestBody UmsRoleParam umsRoleParam, BindingResult result) {
        UmsRole umsRole = umsRoleService.addrole(umsRoleParam);
        if (umsRole != null) {
            return CommonResult.success(umsRole);
        } else {
            return CommonResult.failed("重复命名");
        }
    }

    @ApiOperation(value = "删除角色")
    @GetMapping(value = "/deleterole/{id}")
    @ResponseBody
    public CommonResult deleterole(@PathVariable("id") Long id) {
        int count = umsRoleService.deleterole(id);
        if (count > 0) {
            return CommonResult.success("删除成功");
        } else {
            return CommonResult.failed("删除失败");
        }
    }

    @ApiOperation(value = "角色分配权限")
    @PostMapping(value = "/permission/distribute/{roleid}")
    @ResponseBody
    public CommonResult distribute(@PathVariable(value = "roleid") Long roleid,
                                   @RequestParam(value = "permissionids") List<Long> permissionids){
        int count = umsRoleService.distribute(roleid, permissionids);
        if(count >= 0){
            return CommonResult.success(count);
        }
        else{
            return CommonResult.failed("分配错误");
        }
    }

    @ApiOperation(value = "获取所有角色列表")
    @GetMapping(value = "/getRoleList")
    @ResponseBody
    public CommonResult<CommonPage<UmsRole>> getRoleList(@RequestParam(value = "query", required = false)String name,
                                                      @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                                      @RequestParam(value = "pageNum", defaultValue = "1")Integer pageNum){
        List<UmsRole> umsRoles = umsRoleService.getRoleList(name, pageSize, pageNum);
        return CommonResult.success(CommonPage.resultPage(umsRoles));
    }

    @ApiOperation(value = "更新角色")
    @PostMapping(value = "/updaterole/{RoleID}")
    @ResponseBody
    public CommonResult updaterole(@PathVariable("RoleID") Long RoleID,@Validated @RequestBody UmsRoleParam umsRoleParam, BindingResult result) {
        UmsRole umsRole = umsRoleService.updaterole(RoleID, umsRoleParam);
        if (umsRole != null) {
            return CommonResult.success(umsRole);
        } else {
            return CommonResult.failed("重复命名");
        }
    }

    @ApiOperation(value = "获取角色+权限列表")
    @GetMapping(value = "/getRolePermissionTree")
    @ResponseBody
    public CommonResult<CommonPage<RoleTree>> getRolePermissionTree(@RequestParam(value = "query", required = false)String name,
                                              @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                              @RequestParam(value = "pageNum", defaultValue = "1")Integer pageNum){
        List<RoleTree> umsRoles =  umsRoleService.treeList(name,pageSize, pageNum);
        return CommonResult.success(CommonPage.resultPage(umsRoles));
    }

    @ApiOperation(value = "删除该角色拥有的权限")
    @GetMapping(value = "/DeletePermissionByRoleid/{roleid}")
    @ResponseBody
    public CommonResult DeletePermissionByRoleid(@PathVariable(value = "roleid")Long roleid,
                                                 @RequestParam(value = "permissionid")Long permissionid){
        int count = umsRoleService.DeletePermissionByRoleid(roleid, permissionid);
        if(count > 0){
            return CommonResult.success(count);
        }
        else{
            return CommonResult.failed("删除错误");
        }
    }

    @ApiOperation(value = "更改角色启用状态")
    @RequestMapping(value = "/updateStatu/{roleid}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult updateStatu(@PathVariable("roleid") Long roleid,
                                    @RequestParam("statu") Integer statu ) {
        int count = umsRoleService.updateStatu(roleid, statu);
        if(count > 0){
            return  CommonResult.success(count);
        }
        else{
            return CommonResult.failed("更改失败");
        }
    }

}
