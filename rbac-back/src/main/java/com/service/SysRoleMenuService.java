package com.service;

import com.domain.SysRoleMenu;
    /**
 * @author huiyuan080
 * @date 2023/3/15 
 * @apiNote 
 */
public interface SysRoleMenuService{


    int deleteByPrimaryKey(Long roleId,Long menuId);

    int insert(SysRoleMenu record);

    int insertSelective(SysRoleMenu record);

}
