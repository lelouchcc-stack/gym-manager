package com.lelcc.service;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lelcc.common.BusinessException;
import com.lelcc.common.UserContext;
import com.lelcc.dto.InfoVO;
import com.lelcc.dto.LoginVO;
import com.lelcc.entity.SysUser;
import com.lelcc.mapper.SysUserMapper;
import com.lelcc.utils.JwtUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public SysUser getSysUserByUsername(String username) {

        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser:: getUsername,username);
        return this.getOne(queryWrapper);

    }

    @Override
    public LoginVO login(String username, String password) {
        SysUser sysUser = this.getSysUserByUsername(username);
        if (sysUser == null) {
            throw new BusinessException(401,"用户不存在");
        }
        if(!BCrypt.checkpw(password,sysUser.getPassword())){
           throw new BusinessException(500,"密码错误");

        }

        LoginVO loginVO = new LoginVO();
        loginVO.setId(sysUser.getId());
        loginVO.setToken(jwtUtil.createToken(sysUser.getId()));
        loginVO.setUsername(sysUser.getUsername());

        return loginVO;

    }
    @Resource
    private JwtUtil jwtUtil;


    @Override
    public InfoVO info() {
        Long user_id = UserContext.get();

        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser:: getId,user_id);
        SysUser sys_user = this.getOne(queryWrapper);

        InfoVO info =  new InfoVO();

        info.setNickname(sys_user.getNickname());
        info.setEmail(sys_user.getEmail());
        info.setAvatar(sys_user.getAvatar());
        info.setPhone(sys_user.getPhone());

        return info;


    }
}
