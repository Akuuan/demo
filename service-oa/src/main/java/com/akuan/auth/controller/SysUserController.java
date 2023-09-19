package com.akuan.auth.controller;


import com.akuan.common.utils.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.akuan.auth.service.SysUserService;
import com.akuan.common.result.Result;
import com.atguigu.model.system.SysUser;
import com.atguigu.vo.system.SysUserQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author akuan
 * @since 2023-03-01
 */
@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;


    @ApiOperation("用户条件分页查询")
    @GetMapping("/{page}/{pageSize}")
    public Result page(@PathVariable int page, @PathVariable int pageSize, SysUserQueryVo sysUserQueryVo) {
        Page<SysUser> sysUserPage = new Page<>(page, pageSize);

        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 获取条件
        String userName = sysUserQueryVo.getKeyword();
        String createTimeBegin = sysUserQueryVo.getCreateTimeBegin();
        String createTimeEnd = sysUserQueryVo.getCreateTimeEnd();

        // 判断条件值不为空
        if (!StringUtils.isEmpty(userName)){
            lambdaQueryWrapper.like(SysUser::getUsername,userName);
        }
        if (!StringUtils.isEmpty(createTimeBegin)){
            lambdaQueryWrapper.ge(SysUser::getCreateTime,createTimeBegin);
        }
        if (!StringUtils.isEmpty(createTimeEnd)){
            lambdaQueryWrapper.le(SysUser::getCreateTime,createTimeEnd);
        }

        sysUserService.page(sysUserPage,lambdaQueryWrapper);

        return Result.ok(sysUserPage);
    }


    @ApiOperation("获取用户")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable long id){
        SysUser user = sysUserService.getById(id);
        return Result.ok(user);
    }


    @ApiOperation("更新用户")
    @PutMapping("/update")
    public Result update(@RequestBody SysUser sysUser){
        boolean is_success = sysUserService.updateById(sysUser);
        if (is_success) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }


    @ApiOperation("保存用户")
    @PostMapping("/save")
    public Result save(@RequestBody SysUser sysUser){

        String pwdMD5 = MD5.encrypt(sysUser.getPassword());
        sysUser.setPassword(pwdMD5);
        boolean is_success = sysUserService.save(sysUser);
        if (is_success) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }


    @ApiOperation("删除用户")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable long id){
        boolean is_success = sysUserService.removeById(id);
        if (is_success) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @GetMapping("updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id, @PathVariable Integer status){
        sysUserService.updateStatus(id,status);
        return Result.ok();
    }
}
