package com.mapper;

import com.domain.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author huiyuan080
 * @date 2023/3/15 
 * @apiNote 
 */
public interface SysRoleMapper {
    int deleteByPrimaryKey(Long roleId);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    List<SysRole> getByPage(@Param("params") Map<String, Object> params);

    List<SysRole> sysRoles();

}