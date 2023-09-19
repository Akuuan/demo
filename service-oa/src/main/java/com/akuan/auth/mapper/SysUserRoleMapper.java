package com.akuan.auth.mapper;

import com.atguigu.model.system.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户角色 Mapper 接口
 * </p>
 *
 * @author jerry
 * @since 2023-06-05
 */
@Repository
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

}
