package com.lelcc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lelcc.entity.SysMenu;

import java.util.List;


public interface SysMenuService extends IService<SysMenu> {

    public List<SysMenu> getMenusName(List<Long> menuIds);
    public List<SysMenu> buildMenuTree(List<SysMenu> menus);
}
