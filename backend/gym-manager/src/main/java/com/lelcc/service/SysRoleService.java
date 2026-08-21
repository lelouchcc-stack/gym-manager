package com.lelcc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lelcc.entity.SysRole;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {
    public List<String> getRoleName(List<Long> ids);
}
