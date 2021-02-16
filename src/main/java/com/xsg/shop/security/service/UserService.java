package com.xsg.shop.security.service;

import com.xsg.shop.mbg.model.UmsAdmin;
import com.xsg.shop.mbg.model.UmsPermission;

import java.util.List;

/**
 * 后台管理(用于权限认证+获取一些数据)
 * Created by xsg on 2020/3/19 14:59
 *
 */
public interface UserService {
    /**
     * 根据用户名获取后台管理员
     */
    UmsAdmin getAdminByUsername(String username);


    /**
     * 通过用户id来获取权限
     */
    List<UmsPermission> getPermissionList(Long userid);
}
