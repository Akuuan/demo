package com.akuan.wechat.service;

import com.atguigu.model.wechat.Menu;
import com.atguigu.vo.wechat.MenuVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface MenuService extends IService<Menu> {
    List<MenuVo> findMenuInfo();

    void syncMenu();
}
