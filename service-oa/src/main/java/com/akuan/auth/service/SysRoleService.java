package com.akuan.auth.service;

import com.atguigu.model.system.SysRole;
import com.atguigu.vo.system.AssginRoleVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.expression.spel.ast.Assign;

import java.util.Map;

public interface SysRoleService extends IService<SysRole> {

    Map<String,Object> findRoleDataByUserId(Long Id);

    void doAssign(AssginRoleVo assginRoleVo);
}
