package com.mapper;

import com.domain.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huiyuan080
 * @date 2023/3/15 
 * @apiNote 
 */
public interface SysUserRoleMapper {
    int deleteByPrimaryKey(@Param("userId") Long userId, @Param("roleId") Long roleId);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    Long countUserRoleByUserId(@Param("userId") Long userId);

    int deleteUserRoleByUserId(@Param("userId") Long userId);

    int saveUserRole( List<SysUserRole> list);

    List<Long> getRoleIdsByUserId(@Param("userId") Long userId);
}