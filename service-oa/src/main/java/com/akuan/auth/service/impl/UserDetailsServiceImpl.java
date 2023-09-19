package com.akuan.auth.service.impl;

import com.akuan.auth.service.SysMenuService;
import com.akuan.auth.service.SysUserService;
import com.akuan.common.config.exception.GuiguException;
import com.akuan.common.result.ResultCodeEnum;
import com.akuan.security.custom.CustomUser;
import com.atguigu.model.system.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getByUsername(username);
        if(null == sysUser) {
            throw new UsernameNotFoundException("用户名不存在！");
        }

        if(sysUser.getStatus().intValue() == 0) {
            throw new GuiguException(201,"账号被停用");
        }
        // 根据 user_id 查询用户操作权限数据
        List<String> userPermsList = sysMenuService.findUserPermsByUserId(sysUser.getId());
        // 创建list集合，封装最终权限数据
        List<SimpleGrantedAuthority> authList =  new ArrayList<>();
        // 遍历 authList
        for (String perms : userPermsList) {
            authList.add(new SimpleGrantedAuthority(perms.trim()));
        }

        return new CustomUser(sysUser, authList);}
}
