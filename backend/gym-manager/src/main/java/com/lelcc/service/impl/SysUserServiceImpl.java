package com.lelcc.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lelcc.common.BusinessException;
import com.lelcc.common.UserContext;
import com.lelcc.dto.InfoVO;
import com.lelcc.dto.LoginVO;
import com.lelcc.entity.SysMenu;
import com.lelcc.entity.SysRoleMenu;
import com.lelcc.entity.SysUser;
import com.lelcc.entity.SysUserRole;
import com.lelcc.mapper.SysUserMapper;
import com.lelcc.service.*;
import com.lelcc.utils.JwtUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
        Long userId = UserContext.get();
        SysUser sysUser = this.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser:: getId,userId));

        Set<Long> menuIds = getMenusByUserId(userId);
        List<SysMenu> flatMenus = menuIds.isEmpty()? new ArrayList<>(): sysMenuService.getMenusName(new ArrayList<>(menuIds));

        Set<String> permissions = flatMenus.stream()
                .map(SysMenu::getPerms)
                .filter(p->p != null && !p.isEmpty())
                .collect(Collectors.toSet());

        List<SysMenu> menuTree = sysMenuService.buildMenuTree(flatMenus);

        List<Long> roleIds = sysUserRoleService.getRolesByUserId(userId).stream()
                .map(SysUserRole::getRoleId).toList();
        List<String> roles = roleIds.isEmpty()?new ArrayList<>():sysRoleService.getRoleName(roleIds);

        InfoVO info = new InfoVO();
        info.setUserInfo(Map.of("nickname", sysUser.getNickname()));
        info.setRoleId(roles);
        info.setPermissions(permissions);
        info.setMenu(menuTree);

        return info;


    }
    @Resource
    private SysRoleMenuService sysRoleMenuService;
    @Resource
    private SysUserRoleService sysUserRoleService;
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysRoleService sysRoleService;

    public Set<Long> getMenusByUserId(Long userId){
        List<SysUserRole> userRoles =sysUserRoleService.getRolesByUserId(userId);
        List<Long> roleIds = userRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        List<SysRoleMenu> roleMenus = sysRoleMenuService.getMenusByRoleId(roleIds);


        return roleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toSet());
    }

    public Set<String> getPerms(Set<SysMenu> menus){
        return menus.stream().map(SysMenu::getPerms).collect(Collectors.toSet());
    }
}
