package com.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.mapper.SysRoleMenuMapper;
import com.domain.SysRoleMenu;
import com.service.SysRoleMenuService;
/**
 * @author huiyuan080
 * @date 2023/3/15 
 * @apiNote 
 */
@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService{

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public int deleteByPrimaryKey(Long roleId,Long menuId) {
        return sysRoleMenuMapper.deleteByPrimaryKey(roleId,menuId);
    }

    @Override
    public int insert(SysRoleMenu record) {
        return sysRoleMenuMapper.insert(record);
    }

    @Override
    public int insertSelective(SysRoleMenu record) {
        return sysRoleMenuMapper.insertSelective(record);
    }

}
