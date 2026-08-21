package com.lelcc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lelcc.entity.SysUserRole;
import com.lelcc.mapper.SysUserRoleMapper;

import java.util.List;

public interface SysUserRoleService extends IService<SysUserRole> {
    public List<SysUserRole> getRolesByUserId(Long userId);
}
