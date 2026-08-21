package com.lelcc.dto;

import com.lelcc.entity.SysMenu;
import lombok.Data;

import java.util.*;

@Data
public class InfoVO {
    private Map<String,String> userInfo;
    private List<String> roleId;
    private Set<String> permissions;
    private List<SysMenu> menu;

    public InfoVO() {
        this.roleId=new ArrayList<>();
        this.permissions=new HashSet<>();
        this.menu=new ArrayList<>();
        this.userInfo=new HashMap<>();
    }
}
