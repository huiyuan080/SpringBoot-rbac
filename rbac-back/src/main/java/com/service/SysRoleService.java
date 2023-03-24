package com.service;

import com.common.Page;
import com.domain.SysRole;
import com.enums.ResultEnums;

import java.util.List;

/**
 * @author huiyuan080
 * @date 2023/3/15 
 * @apiNote 
 */
public interface SysRoleService{


    int deleteByPrimaryKey(Long roleId);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);


    Page getByPage(Page page);

    void saveRoleMenu(Long roleId, Long[] menuIds);

    List getCurrentRoleHasMenuIdsByRoleId(Long roleId);

    List<SysRole> getAllRoles();

    void saveUserRoles(Long userId, Long[] roleIds);

    List<Long> getRoleIdsByUserId(Long userId);
}
