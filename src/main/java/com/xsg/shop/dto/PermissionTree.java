package com.xsg.shop.dto;

import com.xsg.shop.mbg.model.UmsPermission;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 权限数
 * Created by xsg on 2020/4/1 20:21
 */

public class PermissionTree extends UmsPermission {
    @Getter
    @Setter
    List<PermissionTree> children;
}
