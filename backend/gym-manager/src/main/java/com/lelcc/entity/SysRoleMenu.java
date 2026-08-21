package com.lelcc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("sys_role_menu")
@Data
public class SysRoleMenu {
    private Long roleId;
    private Long menuId;
}
