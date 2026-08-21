package com.lelcc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lelcc.entity.SysUserRole;
import com.lelcc.mapper.SysUserRoleMapper;
import com.lelcc.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    public List<SysUserRole> getRolesByUserId(Long userId) {

        return this.list(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId,userId));
    }

}
