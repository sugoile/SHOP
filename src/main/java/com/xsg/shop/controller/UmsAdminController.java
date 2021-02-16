package com.xsg.shop.controller;

import com.xsg.shop.common.CommonPage;
import com.xsg.shop.common.CommonResult;
import com.xsg.shop.dto.UmsAdminparam;
import com.xsg.shop.dto.Userparam;
import com.xsg.shop.mbg.model.UmsAdmin;
import com.xsg.shop.mbg.model.UmsRole;
import com.xsg.shop.security.service.UserService;
import com.xsg.shop.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 需要与前面的umsadmincontroller结合（这里先测试）
 * Created by xsg on 2020/3/26 10:27
 */
@Controller
@RequestMapping("/umsadmin")
@Api(tags = "UserAdminController", description = "后台用户管理")
public class UmsAdminController {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Autowired
    private UmsAdminService umsAdminService;

    @ApiOperation(value = "返回后台用户所有管理员(不包括自己)")
    @GetMapping(value = "/getAdmin")
    @ResponseBody
    public CommonResult<CommonPage<UmsAdmin>> getAdmin(@RequestParam(value = "query", required = false)String name,
                                                       @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                                       @RequestParam(value = "pageNum", defaultValue = "1")Integer pageNum,
                                                       @RequestParam(value = "exceptself")String exceptself){
        List<UmsAdmin> umsAdmins = umsAdminService.getAdmin(name, pageSize, pageNum, exceptself);
        return CommonResult.success(CommonPage.resultPage(umsAdmins));
    }

    @ApiOperation(value = "注册用户")
    @PostMapping(value = "/register")
    @ResponseBody
    public CommonResult register(@RequestBody @Validated UmsAdminparam umsAdminparam, BindingResult result){
        UmsAdmin umsAdmin = umsAdminService.register(umsAdminparam);
        if(umsAdmin != null){
            return CommonResult.success(umsAdmin);
        }
        else{
            return CommonResult.failed("用户名重复");
        }
    }

    @ApiOperation(value = "用户分配角色")
    @PostMapping(value = "/role/distribute/{adminid}")
    @ResponseBody
    public CommonResult distribute(@PathVariable(value = "adminid") Long adminid,
                                   @RequestParam(value = "roleids") List<Long> roleids){
        int count = umsAdminService.distribute(adminid, roleids);
        if(count > 0){
            return CommonResult.success(count);
        }
        else{
            return CommonResult.failed("分配错误");
        }
    }

    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestBody @Validated Userparam userparam, BindingResult result) {
        String token = umsAdminService.login(userparam.getUsername(), userparam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("该管理员未存在或未开启");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "更改管理员启用状态")
    @RequestMapping(value = "/updateStatu/{adminid}", method = RequestMethod.GET)
    @ResponseBody
    //@PreAuthorize("hasAuthority('ums:admin:updateStatu')")
    public CommonResult updateStatu(@PathVariable("adminid") Long adminid,
                                    @RequestParam("statu") Integer statu ) {
        int count = umsAdminService.updateStatu(adminid, statu);
        if(count > 0){
            return  CommonResult.success(count);
        }
        else{
            return CommonResult.failed("更改失败");
        }
    }

    @ApiOperation(value = "删除管理员")
    @RequestMapping(value = "/deleteAdmin/{AdminID}", method = RequestMethod.GET)
    @ResponseBody
    //@PreAuthorize("hasAuthority('ums:admin:updateStatu')")
    public CommonResult deleteAdmin(@PathVariable("AdminID") Long AdminID){
        int count = umsAdminService.deleteAdmin(AdminID);
        if(count > 0){
            return CommonResult.success(count);
        }
        else{
            return CommonResult.failed("删除管理员失败");
        }
    }

    @ApiOperation(value = "更新管理员信息")
    @RequestMapping(value = "/updateAdmin/{AdminID}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateAdmin(@PathVariable("AdminID")Long AdminID,
                                    @Validated @RequestBody UmsAdminparam umsAdminparam, BindingResult result){
        UmsAdmin umsAdmin = umsAdminService.updateAdmin(AdminID, umsAdminparam);
        return CommonResult.success(umsAdmin);
    }

    @ApiOperation(value = "返回所有的角色")
    @GetMapping(value = "/getrole")
    @ResponseBody
    public CommonResult getrole(){
        List<UmsRole> umsRoles = umsAdminService.getrole();
        return CommonResult.success(umsRoles);
    }

    @ApiOperation(value = "根据管理员id返回他的所有角色")
    @GetMapping(value = "/getroleByadmin/{adminid}")
    @ResponseBody
    public CommonResult getroleByadmin(@PathVariable("adminid") Long adminid){
        List<UmsRole> umsRoles = umsAdminService.getroleByadmin(adminid);
        return CommonResult.success(umsRoles);
    }
}
