package com.mapper;

import com.domain.SysRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huiyuan080
 * @date 2023/3/15 
 * @apiNote 
 */
public interface SysRoleMenuMapper {
    int deleteByPrimaryKey(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    int insert(SysRoleMenu record);

    int insertSelective(SysRoleMenu record);

    Long countRoleMenusByRoleId(Long roleId);

    int deleteRoleMenusByRoleId(Long roleId);

    int saveRoleMenus(List<SysRoleMenu> sysRoleMenus);

    List<Long> getCurrentRoleHasMenuIdsByRoleId(@Param("roleId") Long roleId);
}