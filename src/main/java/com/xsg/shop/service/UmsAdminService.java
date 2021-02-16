package com.xsg.shop.service;

import com.xsg.shop.dto.UmsAdminparam;
import com.xsg.shop.mbg.model.UmsAdmin;
import com.xsg.shop.mbg.model.UmsRole;

import java.util.List;

/**
 * 后台管理的service
 * Created by xsg on 2020/3/26 10:50
 */
public interface UmsAdminService {
    UmsAdmin register(UmsAdminparam umsAdminparam);

    List<UmsAdmin> getAdmin(String name, Integer pageSize, Integer pageNum, String exceptself);

    int distribute(Long adminid, List<Long> roleids);

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password);

    int updateStatu(Long id, Integer statu);

    int deleteAdmin(Long AdminID);

    UmsAdmin updateAdmin(Long AdminID, UmsAdminparam umsAdminparam);

    List<UmsRole> getrole();

    List<UmsRole> getroleByadmin(Long adminid);
}
