package com.lelcc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("sys_user_role")
@Data
public class SysUserRole {
    private Long userId;
    private Long roleId;
}
