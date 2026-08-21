package com.lelcc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lelcc.entity.SysRole;
import com.lelcc.mapper.SysRoleMapper;
import com.lelcc.service.SysRoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public List<String> getRoleName(List<Long> ids) {
        if(ids==null||ids.isEmpty()) return new ArrayList<>();
        return this.list(new LambdaQueryWrapper<SysRole>().in(SysRole::getId,ids))
                .stream().map(SysRole::getRoleName).collect(Collectors.toList());

    }
}
