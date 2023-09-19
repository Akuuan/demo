package com.akuan.auth.controller;


import com.akuan.auth.service.SysRoleService;
import com.akuan.common.result.Result;
import com.atguigu.model.system.SysRole;
import com.atguigu.vo.system.AssginRoleVo;
import com.atguigu.vo.system.SysRoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("/toAssign/{userId}")
    public Result toAssign(@PathVariable Long userId){
        Map<String, Object> map = sysRoleService.findRoleDataByUserId(userId);
        return Result.ok(map);
    }

    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleVo assginRoleVo) {
        sysRoleService.doAssign(assginRoleVo);
        return Result.ok();
    }

    //查询所有角色 和 当前用户所属角色
    /*@GetMapping("/findAll")
    public List<SysRole> findAll(){
        List<SysRole> roleList = sysRoleService.list();
        return roleList;

    }*/
    @GetMapping("/findAll")
    public Result findAll(){
        List<SysRole> roleList = sysRoleService.list();
        return Result.ok(roleList);

    }
    //page 当前页 limit 每页记录数
    //条件分页查询
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @GetMapping("{page}/{limit}")
    public Result pageQueryRole(@PathVariable Long page,
                                @PathVariable Long limit,
                                SysRoleQueryVo sysRoleQueryVo){
        Page<SysRole> pageParam = new Page<>(page,limit);
        String roleName = sysRoleQueryVo.getRoleName();
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(roleName)){
            wrapper.like(SysRole::getRoleName,roleName);
        }

        IPage<SysRole> pageModel = sysRoleService.page(pageParam,wrapper);
        return Result.ok(pageModel);
    }

    //添加角色
    @PreAuthorize("hasAuthority('bnt.sysRole.add')")
    @PostMapping("save")
    public Result save(@RequestBody SysRole role){

        boolean is_success = sysRoleService.save(role);
        if (is_success){
           return Result.ok();
        }
        else {
            return Result.fail();
        }
    }

    //根据id查询
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id){
        SysRole sysRole = sysRoleService.getById(id);
        return Result.ok(sysRole);
    }


    //修改角色
    @PreAuthorize("hasAuthority('bnt.sysRole.update')")
    @PutMapping("update")
    public Result update(@RequestBody SysRole role){

        boolean is_success = sysRoleService.updateById(role);
        if (is_success){
            return Result.ok();
        }
        else {
            return Result.fail();
        }
    }

    //按照id删除
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id){
        boolean is_success = sysRoleService.removeById(id);
        if (is_success){
            return Result.ok();
        }
        else {
            return Result.fail();
        }
    }

    //批量删除
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @DeleteMapping("batchRemove")
    public Result remove(@RequestBody List<Long> idList){
        boolean is_success = sysRoleService.removeByIds(idList);
        if (is_success){
            return Result.ok();
        }
        else {
            return Result.fail();
        }
    }
}
