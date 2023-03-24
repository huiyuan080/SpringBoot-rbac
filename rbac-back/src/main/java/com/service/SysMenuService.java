package com.service;

import com.domain.SysMenu;
import com.domain.SysUser;
import com.vo.RouterVo;

import java.util.List;

/**
 * @author huiyuan080
 * @date 2023/3/13 
 * @apiNote 
 */
public interface SysMenuService{


    int deleteByPrimaryKey(Long menuId);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Long menuId);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

        List<SysMenu> listMenu(SysMenu sysMenu, SysUser sessionUser);

    Long countChildrenByParentId(Long menuId);

    List<SysMenu> listMenuMC(SysUser sessionUser);

    List<RouterVo> getRouters(SysUser sessionUser);
}
