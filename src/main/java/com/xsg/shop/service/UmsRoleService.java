package com.xsg.shop.service;

import com.xsg.shop.dto.RoleTree;
import com.xsg.shop.dto.UmsRoleParam;
import com.xsg.shop.mbg.model.UmsRole;

import java.util.List;

/**
 * 角色管理service
 * Created by xsg on 2020/3/26 16:09
 */
public interface UmsRoleService {
    UmsRole addrole(UmsRoleParam umsRoleParam);

    int deleterole(Long id);

    int distribute(Long roleid, List<Long> permissionids);

    List<UmsRole> getRoleList(String name, Integer pageSize, Integer pageNum);

    UmsRole updaterole(Long RoleID, UmsRoleParam umsRoleParam);

    List<RoleTree> treeList(String name, Integer pageSize, Integer pageNum);

    int DeletePermissionByRoleid(Long roleid, Long permissionid);

    int updateStatu(Long roleid, Integer statu);
}
