package com.service;

import com.domain.SysUserRole;
    /**
 * @author huiyuan080
 * @date 2023/3/15 
 * @apiNote 
 */
public interface SysUserRoleService{


    int deleteByPrimaryKey(Long userId,Long roleId);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

}
