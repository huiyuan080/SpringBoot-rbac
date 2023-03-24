package com.mapper;

import com.domain.SysUser;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author huiyuan080
 * @date 2023/3/7
 * @apiNote
 */
public interface SysUserMapper {

    int deleteByPrimaryKey(Long userId);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser getUserByUserNameAndPassword(@Param("username") String username,@Param("password") String password);

    List<String> queryUserPermissions(@Param("userId") Long userId);

    List<SysUser> getByPage(@Param("params") Map<String, Object> params);

    SysUser selectByUserName(@Param("userName") String userName);


}