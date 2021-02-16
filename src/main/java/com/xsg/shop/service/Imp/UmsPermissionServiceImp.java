package com.xsg.shop.service.Imp;

import cn.hutool.core.bean.BeanUtil;
import com.xsg.shop.dao.UserAdminRelationDao;
import com.xsg.shop.dto.PermissionTree;
import com.xsg.shop.dto.UmsPermissionParam;
import com.xsg.shop.mbg.mapper.UmsPermissionMapper;
import com.xsg.shop.mbg.model.UmsPermission;
import com.xsg.shop.mbg.model.UmsPermissionExample;
import com.xsg.shop.service.UmsPermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xsg on 2020/3/26 19:24
 */
@Service
public class UmsPermissionServiceImp implements UmsPermissionService {
    @Autowired
    private UmsPermissionMapper umsPermissionMapper;
    @Autowired
    private UserAdminRelationDao userAdminRelationDao;

    @Override
    public UmsPermission addpermission(UmsPermissionParam umsPermissionParam) {
        UmsPermission umsPermission = new UmsPermission();
        BeanUtils.copyProperties(umsPermissionParam, umsPermission);
        umsPermission.setCreateTime(new Date());

       //根据父类id来设置级别
        if(umsPermissionParam.getParentid() == 0){
            umsPermission.setLevel(0);
        }
        else {
           UmsPermission umsPermission1 = umsPermissionMapper.selectByPrimaryKey(umsPermissionParam.getParentid());
           if(umsPermission1 != null){
               umsPermission.setLevel(umsPermission1.getLevel() + 1);
           }
           else {
               umsPermission.setLevel(0);
               umsPermission.setParentid((long) 0);
           }
        }
        umsPermissionMapper.insert(umsPermission);
        return umsPermission;
    }

    @Override
    public List<UmsPermission> getList() {
        return umsPermissionMapper.selectByExample(new UmsPermissionExample());
    }

    @Override
    public List<PermissionTree> getTree() {
        UmsPermissionExample umsPermissionExample = new UmsPermissionExample();
        List<UmsPermission>permissions = umsPermissionMapper.selectByExample(umsPermissionExample);
        List<PermissionTree> Trees = new ArrayList<>();
        permissions.forEach(Permission -> {
            PermissionTree permissionTree = new PermissionTree();
            BeanUtil.copyProperties(Permission, permissionTree);
            Trees.add(permissionTree);
        });
        List<PermissionTree> treeList = bulid(Trees);
        return treeList;
    }

    @Override
    public List<PermissionTree> getTreeByRoleID(Long roleid) {
        List<UmsPermission> umsPermissions = userAdminRelationDao.getPermissionByRoleId(roleid);
        List<PermissionTree> permissionTrees = new ArrayList<>();
        umsPermissions.forEach(umsPermission -> {
            PermissionTree permissionTree = new PermissionTree();
            BeanUtil.copyProperties(umsPermission, permissionTree);
            permissionTrees.add(permissionTree);
        });
        return bulid(permissionTrees);
    }

    //双层for循环返回树结构权限
    public  List<PermissionTree> bulid(List<PermissionTree> treeNodes) {
        List<PermissionTree> trees = new ArrayList<PermissionTree>();
        for (PermissionTree treeNode : treeNodes) {
            if (treeNode.getParentid() == 0) {
                trees.add(treeNode);
            }
            for (PermissionTree it : treeNodes) {
                if (it.getParentid() == treeNode.getId() ) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList<PermissionTree>());
                    }
                    treeNode.getChildren().add(it);
                }
            }
        }
        return trees;
    }

}
