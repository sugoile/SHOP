package com.xsg.shop.security.service.Imp;

import com.xsg.shop.dao.UserAdminRelationDao;
import com.xsg.shop.mbg.mapper.UmsAdminMapper;
import com.xsg.shop.mbg.model.*;
import com.xsg.shop.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xsg on 2020/3/19 15:00
 */
@Service
public class UserServiceImp implements UserService{
    @Autowired
    private UmsAdminMapper umsAdminMapper;
    @Autowired
    private UserAdminRelationDao userAdminRelationDao;

    @Override
    public UmsAdmin getAdminByUsername(String username) {
        UmsAdminExample umsAdminExample = new UmsAdminExample();
        umsAdminExample.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> umsAdmins =  umsAdminMapper.selectByExample(umsAdminExample);
        if(umsAdmins != null && umsAdmins.size() > 0){
            return umsAdmins.get(0);
        }
        return null;
    }

    @Override
    public  List<UmsPermission> getPermissionList(Long id) {
       return userAdminRelationDao.getPermissionList(id);
    }
}
