package com.xsg.shop.service.Imp;

import com.github.pagehelper.PageHelper;
import com.xsg.shop.dao.UserAdminRelationDao;
import com.xsg.shop.dto.PermissionTree;
import com.xsg.shop.dto.RoleTree;
import com.xsg.shop.dto.UmsRoleParam;
import com.xsg.shop.mbg.mapper.UmsPermissionMapper;
import com.xsg.shop.mbg.mapper.UmsRoleMapper;
import com.xsg.shop.mbg.mapper.UmsRolePermissionRelationMapper;
import com.xsg.shop.mbg.model.*;
import com.xsg.shop.service.UmsRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.management.relation.Role;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xsg on 2020/3/26 16:10
 */
@Service
public class UmsRoleServicemp implements UmsRoleService {
    @Autowired
    private UmsRoleMapper umsRoleMapper;
    @Autowired
    private UmsRolePermissionRelationMapper umsRolePermissionRelationMapper;
    @Autowired
    private UmsPermissionMapper umsPermissionMapper;
    @Autowired
    private UserAdminRelationDao userAdminRelationDao;

    @Override
    public UmsRole addrole(UmsRoleParam umsRoleParam) {
        UmsRole umsRole = new UmsRole();
        umsRole.setName(umsRoleParam.getName());
        umsRole.setDescription(umsRoleParam.getDescription());
        umsRole.setCreateTime(new Date());
        if (umsRoleParam.getStatus() == null) {
            umsRole.setStatus(1);
        } else {
            umsRole.setStatus(umsRoleParam.getStatus());
        }
        umsRole.setAdminCount(0);
        umsRoleMapper.insertSelective(umsRole);
        return umsRole;
    }

    @Override
    public int deleterole(Long id) {
        return umsRoleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int distribute(Long roleid, List<Long> permissionids) {
        int count = -1;
        if (umsRoleMapper.selectByPrimaryKey(roleid) != null) {
                //删除原来绑定的关系
                UmsRolePermissionRelationExample umsRolePermissionRelationExample = new UmsRolePermissionRelationExample();
                umsRolePermissionRelationExample.createCriteria().andRoleIdEqualTo(roleid);
                umsRolePermissionRelationMapper.deleteByExample(umsRolePermissionRelationExample);
                //传permissionids数组为空时
                count = 0;

            if (!CollectionUtils.isEmpty(permissionids)) {
                for (Long permissionid : permissionids) {
                    //这里如果role中有存在时添加进去，不存在则不添加
                    if (umsRoleMapper.selectByPrimaryKey(roleid) != null) {
                        UmsRolePermissionRelation umsRolePermissionRelation = new UmsRolePermissionRelation();
                        umsRolePermissionRelation.setRoleId(roleid);
                        umsRolePermissionRelation.setPermissionId(permissionid);
                        umsRolePermissionRelationMapper.insert(umsRolePermissionRelation);
                        count++;
                    }
                }
            }
        }
        return count;
    }

    @Override
    public List<UmsRole> getRoleList(String name, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        UmsRoleExample umsRoleExample = new UmsRoleExample();
        if (!StringUtils.isEmpty(name)) {
            umsRoleExample.createCriteria().andNameLike("%" + name + "%");
            umsRoleExample.or(umsRoleExample.createCriteria().andDescriptionLike("%" + name + "%"));
        }
        return umsRoleMapper.selectByExample(umsRoleExample);
    }

    @Override
    public UmsRole updaterole(Long RoleID, UmsRoleParam umsRoleParam) {
        UmsRole umsRole = new UmsRole();
        BeanUtils.copyProperties(umsRoleParam, umsRole);
        umsRole.setId(RoleID);
        int count = umsRoleMapper.updateByPrimaryKeySelective(umsRole);
        if (count > 0) return umsRole;
        else return null;
    }

    @Override
    public List<RoleTree> treeList(String name, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        UmsRoleExample umsRoleExample = new UmsRoleExample();
        if (!StringUtils.isEmpty(name)) {
            umsRoleExample.createCriteria().andNameLike("%" + name + "%");
            umsRoleExample.or(umsRoleExample.createCriteria().andDescriptionLike("%" + name + "%"));
        }
        List<UmsRole> umsroles = umsRoleMapper.selectByExample(umsRoleExample);
        List<RoleTree> roleTrees = new ArrayList<>();
        umsroles.forEach(umsrole -> {
            RoleTree roleTree = new RoleTree();
            BeanUtils.copyProperties(umsrole, roleTree);
            roleTrees.add(roleTree);
        });
        BeanUtils.copyProperties(umsroles, roleTrees);
        for (int i = 0; i<roleTrees.size(); i++) {
            List<UmsPermission> umsPermissions = userAdminRelationDao.getPermissionByRoleId(roleTrees.get(i).getId());
            List<PermissionTree> Trees = new ArrayList<>();
            if (umsPermissions.size() != 0 && umsPermissions != null){
                List<PermissionTree> permissionTrees = new ArrayList<>();
                umsPermissions.forEach(tree ->{
                    PermissionTree permissionTree = new PermissionTree();
                    BeanUtils.copyProperties(tree, permissionTree);
                    permissionTrees.add(permissionTree);
                });
                Trees = buildTree(permissionTrees);
            }
            roleTrees.get(i).setPermissionTrees(Trees);
        }
        return roleTrees;
    }

    @Override
    public int DeletePermissionByRoleid(Long roleid, Long permissionid) {
        UmsPermissionExample umsPermissionExample = new UmsPermissionExample();
        List<UmsPermission>umsPermissions = umsPermissionMapper.selectByExample(umsPermissionExample);
        List<Long> nullList = new ArrayList<>();
        List<Long> permissions = findChildrenByPermissionid(permissionid, umsPermissions, nullList);
        permissions.add(permissionid);
        UmsRolePermissionRelationExample umsRolePermissionRelationExample = new UmsRolePermissionRelationExample();
        umsRolePermissionRelationExample.createCriteria().andRoleIdEqualTo(roleid).andPermissionIdIn(permissions);
        return umsRolePermissionRelationMapper.deleteByExample(umsRolePermissionRelationExample);
    }

    @Override
    public int updateStatu(Long roleid, Integer statu) {
        UmsRole umsRole = new UmsRole();
        umsRole.setStatus(statu);

        UmsRoleExample umsRoleExample = new UmsRoleExample();
        umsRoleExample.createCriteria().andIdEqualTo(roleid);
        return umsRoleMapper.updateByExampleSelective(umsRole, umsRoleExample);
    }

    /*根据一个权限的id找出所有包含的子id返回*/
    public List<Long> findChildrenByPermissionid(Long permissionid, List<UmsPermission> umsPermissions, List<Long> returnper){
        for (UmsPermission permission : umsPermissions) {
            if(permission.getParentid() == permissionid) {
                returnper.add(permission.getId());
                findChildrenByPermissionid(permission.getId(), umsPermissions, returnper);
            }
        }
        return returnper;
    }

    //递归函数返回树结构
    public List<PermissionTree> buildTree(List<PermissionTree> treeNodes){
        List<PermissionTree> trees = new ArrayList<>();
        for (PermissionTree treeNode : treeNodes) {
            if (treeNode.getParentid() == 0) {
                trees.add(findChildren(treeNode,treeNodes));
            }
        }
        return trees;
    }

    public PermissionTree findChildren(PermissionTree treeNode,List<PermissionTree> treeNodes){
        for (PermissionTree tree : treeNodes) {
            if(treeNode.getId() == tree.getParentid()) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<PermissionTree>());
                }
                treeNode.getChildren().add(findChildren(tree,treeNodes));
            }
        }
        return treeNode;
    }
}
