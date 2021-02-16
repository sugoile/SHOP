package com.xsg.shop.dto;

import com.xsg.shop.mbg.model.UmsRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 角色带权限树
 * Created by xsg on 2020/4/1 20:22
 */
public class RoleTree extends UmsRole {
    @Getter
    @Setter
    List<PermissionTree> permissionTrees;
}
