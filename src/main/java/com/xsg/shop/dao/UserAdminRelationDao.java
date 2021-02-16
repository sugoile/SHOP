package com.xsg.shop.dao;

import com.xsg.shop.mbg.model.UmsPermission;
import com.xsg.shop.mbg.model.UmsRole;

import java.util.List;

/**
 * 用户映射关系处理
 * Created by xsg on 2020/3/19 20:47
 */
public interface UserAdminRelationDao {
    /**
     * * 获取用户所有权限
     */
    List<UmsPermission> getPermissionList(Long userid);

    /**
     * 通过管理员id返回角色列表
     */
    List<UmsRole> getroleByadmin(Long adminid);

    /**
     * 通过角色id来返回该拥有的全部权限
     */
    List<UmsPermission> getPermissionByRoleId(Long RoleId);

}
