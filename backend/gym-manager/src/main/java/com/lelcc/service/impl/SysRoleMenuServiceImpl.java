package com.lelcc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lelcc.entity.SysRoleMenu;
import com.lelcc.mapper.SysRoleMenuMapper;
import com.lelcc.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper,SysRoleMenu> implements SysRoleMenuService {
    public List<SysRoleMenu> getMenusByRoleId(List<Long> roleIds)
    {
        if (roleIds == null || roleIds.isEmpty()) {
            return Collections.emptyList();   // 空直接返回空，别让 SQL 生成 IN ()
        }
        return this.list(new LambdaQueryWrapper<SysRoleMenu>().in(SysRoleMenu::getRoleId,roleIds)) ;
    }
}
