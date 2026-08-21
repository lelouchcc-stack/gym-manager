package com.lelcc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lelcc.entity.SysRoleMenu;

import java.util.List;

public interface SysRoleMenuService extends IService<SysRoleMenu> {
    public List<SysRoleMenu> getMenusByRoleId(List<Long> roleIds);
}
