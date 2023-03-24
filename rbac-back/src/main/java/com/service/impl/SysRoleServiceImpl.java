package com.service.impl;

import com.common.Page;
import com.domain.SysRoleMenu;
import com.domain.SysUserRole;
import com.github.pagehelper.PageHelper;
import com.mapper.SysRoleMenuMapper;
import com.mapper.SysUserRoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.mapper.SysRoleMapper;
import com.domain.SysRole;
import com.service.SysRoleService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huiyuan080
 * @date 2023/3/15
 * @apiNote
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public int deleteByPrimaryKey(Long roleId) {
        return sysRoleMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    public int insert(SysRole record) {
        return sysRoleMapper.insert(record);
    }

    @Override
    public int insertSelective(SysRole record) {
        return sysRoleMapper.insertSelective(record);
    }

    @Override
    public SysRole selectByPrimaryKey(Long roleId) {
        return sysRoleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public int updateByPrimaryKeySelective(SysRole record) {
        return sysRoleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public Page getByPage(Page page) {
        com.github.pagehelper.Page<Object> pageHelper = PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        sysRoleMapper.getByPage(page.getParams());
        page.setList(pageHelper.getResult());
        page.setTotalCount(pageHelper.getTotal());
        return page;
    }

    @Override
    public int updateByPrimaryKey(SysRole record) {
        return sysRoleMapper.updateByPrimaryKey(record);
    }


    @Override
    public void saveRoleMenu(Long roleId, Long[] menuIds) {
        /*
         * 先删除角色原始权限记录(角色菜单记录)
         * 批量添加新的角色菜单记录
         */
        //先查询  如果有 先把以前的进行删除
        Long count = sysRoleMenuMapper.countRoleMenusByRoleId(roleId);
        if (count > 0) {
            sysRoleMenuMapper.deleteRoleMenusByRoleId(roleId);
        }

     /*   for (Long menuId : menuIds) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenuMapper.insertSelective(sysRoleMenu);
        }
*/

         /*
            再执行批量添加
         */
        List<SysRoleMenu> sysRoleMenus = new ArrayList<>();
        for (Long menuId : menuIds) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenus.add(sysRoleMenu);
        }
        sysRoleMenuMapper.saveRoleMenus(sysRoleMenus);
    }

    @Override
    public void saveUserRoles(Long userId, Long[] roleIds) {
        Long count = sysUserRoleMapper.countUserRoleByUserId(userId);
        if (count > 0) {
            sysUserRoleMapper.deleteUserRoleByUserId(userId);
        }
        List<SysUserRole> sysUserRoles = new ArrayList<>();
        for (Long roleId : roleIds) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(roleId);
            sysUserRoles.add(sysUserRole);
        }
        sysUserRoleMapper.saveUserRole(sysUserRoles);
    }

    @Override
    public List<SysRole> getAllRoles() {
        return sysRoleMapper.sysRoles();
    }

    @Override
    public List getCurrentRoleHasMenuIdsByRoleId(Long roleId) {
        return sysRoleMenuMapper.getCurrentRoleHasMenuIdsByRoleId(roleId);
    }

    @Override
    public List<Long> getRoleIdsByUserId(Long userId) {
        return sysUserRoleMapper.getRoleIdsByUserId(userId);
    }
}
