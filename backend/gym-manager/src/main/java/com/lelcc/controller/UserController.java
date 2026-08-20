package com.lelcc.controller;


import com.lelcc.common.Result;
import com.lelcc.common.UserContext;
import com.lelcc.dto.InfoVO;
import com.lelcc.dto.LoginDTO;
import com.lelcc.dto.LoginVO;
import com.lelcc.entity.SysUser;
import com.lelcc.service.SysUserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
public class UserController {
    @Resource
    private SysUserService sysUserService;

    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO loginDTO) {

        String input_pwd = loginDTO.getPassword();
        String input_uname = loginDTO.getUsername();

        LoginVO select_user = sysUserService.login(input_uname, input_pwd);

        return Result.success(select_user);

    }
    @PostMapping("/info")
    public Result<InfoVO> info() {
        return Result.success(sysUserService.info());
    }
}
