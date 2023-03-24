package com.mapper;

import com.domain.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huiyuan080
 * @date 2023/3/13
 * @apiNote
 */
public interface SysMenuMapper {
    int deleteByPrimaryKey(Long menuId);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Long menuId);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

    List<SysMenu> listMenu(@Param("sysMenu") SysMenu sysMenu);

    List<SysMenu> listMenuByUserId(@Param("sysMenu") SysMenu sysMenu, @Param("userId") Long userId);

    Long queryMaxIdByMenuTypeAndParentId(@Param("menuType") Integer menuType,@Param("parentId") Long parentId);

    Long countChildrenByParentId(@Param("menuId") Long menuId);

    List<SysMenu> queryAllMenuMC();

    List<SysMenu> queryAllMenuMCByUserId(@Param("userId") Long userId);
}