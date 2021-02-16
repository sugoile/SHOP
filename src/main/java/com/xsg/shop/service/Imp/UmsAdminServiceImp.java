package com.xsg.shop.service.Imp;

import com.github.pagehelper.PageHelper;
import com.xsg.shop.dao.UserAdminRelationDao;
import com.xsg.shop.dto.UmsAdminparam;
import com.xsg.shop.mbg.mapper.UmsAdminMapper;
import com.xsg.shop.mbg.mapper.UmsAdminRoleRelationMapper;
import com.xsg.shop.mbg.mapper.UmsRoleMapper;
import com.xsg.shop.mbg.model.*;
import com.xsg.shop.security.JwtTokenUtil;
import com.xsg.shop.service.UmsAdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by xsg on 2020/3/26 10:51
 */
@Service
public class UmsAdminServiceImp implements UmsAdminService {

    @Autowired
    private UmsAdminMapper umsAdminMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UmsAdminRoleRelationMapper umsAdminRoleRelationMapper;
    @Autowired
    private UmsRoleMapper umsRoleMapper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserAdminRelationDao userAdminRelationDao;

    @Override
    public UmsAdmin register(UmsAdminparam umsAdminparam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminparam, umsAdmin);
        umsAdmin.setCreateTime(new Date());
        //查询是否有相同用户名的用户
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(umsAdmin.getUsername());
        List<UmsAdmin> umsAdminList = umsAdminMapper.selectByExample(example);
        if (umsAdminList.size() > 0) {
            return null;
        }
        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        umsAdminMapper.insertSelective(umsAdmin);
        return umsAdmin;
    }

    @Override
    public List<UmsAdmin> getAdmin(String name, Integer pageSize, Integer pageNum, String exceptself) {
        PageHelper.startPage(pageNum, pageSize);
        UmsAdminExample umsAdminExample = new UmsAdminExample();
        if (!StringUtils.isEmpty(name)) {
            umsAdminExample.createCriteria().andUsernameLike("%" + name + "%").andUsernameNotEqualTo(exceptself);
            umsAdminExample.or(umsAdminExample.createCriteria().andNoteLike("%" + name + "%").andUsernameNotEqualTo(exceptself));
        } else {
            umsAdminExample.createCriteria().andUsernameNotEqualTo(exceptself);
        }
        List<UmsAdmin> umsAdmins = umsAdminMapper.selectByExample(umsAdminExample);
        return umsAdmins;
    }

    @Override
    public int distribute(Long adminid, List<Long> roleids) {
        int count = 0;
        if (umsAdminMapper.selectByPrimaryKey(adminid) != null) {
            //删除原来绑定的关系
            UmsAdminRoleRelationExample umsAdminRoleRelationExample = new UmsAdminRoleRelationExample();
            umsAdminRoleRelationExample.createCriteria().andAdminIdEqualTo(adminid);
            umsAdminRoleRelationMapper.deleteByExample(umsAdminRoleRelationExample);

            if (!CollectionUtils.isEmpty(roleids)) {
                for (Long roleid : roleids) {
                    //这里如果role中有存在时添加进去，不存在则不添加
                    if (umsRoleMapper.selectByPrimaryKey(roleid) != null) {
                        UmsAdminRoleRelation umsAdminRoleRelation = new UmsAdminRoleRelation();
                        umsAdminRoleRelation.setAdminId(adminid);
                        umsAdminRoleRelation.setRoleId(roleid);
                        umsAdminRoleRelationMapper.insert(umsAdminRoleRelation);
                        count++;
                    }
                }
            }
        }
        return count;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            if (userDetails.isEnabled() == false) {
                throw new Exception();
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
//            updateLoginTimeByUsername(username);
        } catch (AuthenticationException e) {
        } catch (Exception e) {
        }
        return token;
    }

    @Override
    public int updateStatu(Long id, Integer statu) {
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setStatus(statu);

        UmsAdminExample umsAdminExample = new UmsAdminExample();
        umsAdminExample.createCriteria().andIdEqualTo(id);
        return umsAdminMapper.updateByExampleSelective(umsAdmin, umsAdminExample);
    }

    @Override
    public int deleteAdmin(Long AdminID) {
        //删除admin与role的联系
        UmsAdminRoleRelationExample umsAdminRoleRelationExample = new UmsAdminRoleRelationExample();
        umsAdminRoleRelationExample.createCriteria().andAdminIdEqualTo(AdminID);
        umsAdminRoleRelationMapper.deleteByExample(umsAdminRoleRelationExample);
        return umsAdminMapper.deleteByPrimaryKey(AdminID);
    }

    @Override
    public UmsAdmin updateAdmin(Long AdminID, UmsAdminparam umsAdminparam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminparam, umsAdmin);
        umsAdmin.setId(AdminID);
        umsAdmin.setPassword(null);
        int count = umsAdminMapper.updateByPrimaryKeySelective(umsAdmin);
        if (count > 0) return umsAdmin;
        else return null;
    }

    @Override
    public List<UmsRole> getrole() {
        UmsRoleExample umsRoleExample = new UmsRoleExample();
        umsRoleExample.createCriteria().andStatusNotEqualTo(0);
        return umsRoleMapper.selectByExample(umsRoleExample);
    }

    @Override
    public List<UmsRole> getroleByadmin(Long adminid) {
        return userAdminRelationDao.getroleByadmin(adminid);
    }


}
