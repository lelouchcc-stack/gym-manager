package com.lelcc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("sys_user")
@Data
public class SysUser {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String avatar;
    private String nickname;
    private Integer status;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
    private String remark;

}
