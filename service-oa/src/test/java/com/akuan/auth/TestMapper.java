package com.akuan.auth;


import com.akuan.auth.mapper.SysRoleMapper;
import com.atguigu.model.system.SysRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestMapper {
    //注入
    @Autowired
    private SysRoleMapper mapper;
    @Test
    public void getAll(){
        List<SysRole> list = mapper.selectList(null);
        System.out.println(list);
    }
    @Test
    public void add(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("角色管理");
        sysRole.setRoleCode("role2");
        sysRole.setDescription("角色管理");

        int rows = mapper.insert(sysRole);
        System.out.println(rows);
        System.out.println(sysRole);
    }
}
