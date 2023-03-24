package com.service;

import com.common.Page;
import com.domain.SysUser;

import java.util.List;

/**
 * @author huiyuan080
 * @date 2023/3/7
 * @apiNote
 */
public interface SysUserService {


    int deleteByPrimaryKey(Long userId);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser login(String username, String password);

    List<String> queryUserPermissions(Long userId);

    Page getByPage(Page page);

    SysUser selectByUserName(String userName);



}
