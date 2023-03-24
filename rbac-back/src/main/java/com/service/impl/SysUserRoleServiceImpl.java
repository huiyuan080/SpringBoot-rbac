package com.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.mapper.SysUserRoleMapper;
import com.domain.SysUserRole;
import com.service.SysUserRoleService;
/**
 * @author huiyuan080
 * @date 2023/3/15 
 * @apiNote 
 */
@Service
public class SysUserRoleServiceImpl implements SysUserRoleService{

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public int deleteByPrimaryKey(Long userId,Long roleId) {
        return sysUserRoleMapper.deleteByPrimaryKey(userId,roleId);
    }

    @Override
    public int insert(SysUserRole record) {
        return sysUserRoleMapper.insert(record);
    }

    @Override
    public int insertSelective(SysUserRole record) {
        return sysUserRoleMapper.insertSelective(record);
    }

}
