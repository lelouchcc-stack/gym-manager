package com.lelcc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lelcc.entity.SysMenu;
import com.lelcc.mapper.SysMenuMapper;
import com.lelcc.service.SysMenuService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper,SysMenu> implements SysMenuService {


    public List<SysMenu> getMenusName(List<Long> menuIds){

        return this.list(new LambdaQueryWrapper<SysMenu>().in(SysMenu::getId,menuIds));
    }

    public List<SysMenu> buildMenuTree(List<SysMenu> menus){
        //list转map
        Map<Long,SysMenu> map = menus.stream().collect(Collectors.toMap(SysMenu::getId,menu->menu));
        //对list的每个元素的子节点这一属性，设置为空
        menus.forEach(menu->{menu.setChildren(new ArrayList<>());});

        List<SysMenu> roots = new ArrayList<>();
        for(SysMenu sysMenu:menus){
            SysMenu parent = map.get(sysMenu.getParentId());
            // 如果A有父级节点，则将父节点的属性（子节点）添加A。否则将A塞到一个空地
            if(parent!=null){
                parent.getChildren().add(sysMenu);
            }else {
                roots.add(sysMenu);
            }
        }
        sortMenu(roots);
        return roots;

    }

    private void sortMenu(List<SysMenu> menus){
        //比较 list里每个元素的sort属性进行排序
        menus.sort(Comparator.comparing(SysMenu::getSort));
        //再对以sort属性排好序的元素，按照元素的子节点属性排序
        menus.forEach(menu->{sortMenu(menu.getChildren());});
    }
}
