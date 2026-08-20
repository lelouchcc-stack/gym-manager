package com.lelcc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lelcc.dto.InfoVO;
import com.lelcc.dto.LoginVO;
import com.lelcc.entity.SysUser;

public interface SysUserService extends IService<SysUser> {
    SysUser getSysUserByUsername(String username);
    LoginVO login(String username, String password);
    InfoVO info();
}
