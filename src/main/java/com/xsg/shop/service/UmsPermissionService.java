package com.xsg.shop.service;

import com.xsg.shop.dto.PermissionTree;
import com.xsg.shop.dto.UmsPermissionParam;
import com.xsg.shop.mbg.model.UmsPermission;

import java.util.List;

/**
 * 权限管理service
 * Created by xsg on 2020/3/26 19:22
 */
public interface UmsPermissionService {
    UmsPermission addpermission(UmsPermissionParam umsPermissionParam);

    List<UmsPermission> getList();

    List<PermissionTree> getTree();

    List<PermissionTree> getTreeByRoleID(Long roleid);

}
