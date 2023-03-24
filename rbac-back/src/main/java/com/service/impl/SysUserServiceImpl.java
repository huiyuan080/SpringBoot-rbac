package com.service.impl;

import com.common.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.mapper.SysUserMapper;
import com.domain.SysUser;
import com.service.SysUserService;

import java.util.List;

/**
 * @author huiyuan080
 * @date 2023/3/7
 * @apiNote
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public int deleteByPrimaryKey(Long userId) {
        return sysUserMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public int insert(SysUser record) {
        return sysUserMapper.insert(record);
    }

    @Override
    public int insertSelective(SysUser record) {
        return sysUserMapper.insertSelective(record);
    }

    @Override
    public SysUser selectByPrimaryKey(Long userId) {
        return sysUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public int updateByPrimaryKeySelective(SysUser record) {
        return sysUserMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysUser record) {
        return sysUserMapper.updateByPrimaryKey(record);
    }

    @Override
    public SysUser login(String username, String password) {
        return sysUserMapper.getUserByUserNameAndPassword(username, password);
    }

    @Override
    public List<String> queryUserPermissions(Long userId) {
        return sysUserMapper.queryUserPermissions(userId);
    }

    @Override
    public Page getByPage(Page page) {
        com.github.pagehelper.Page<Object> pageHelper = PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        sysUserMapper.getByPage(page.getParams());
        page.setList(pageHelper.getResult());
        page.setTotalCount(pageHelper.getTotal());
        return page;
    }

    @Override
    public SysUser selectByUserName(String userName) {
        return sysUserMapper.selectByUserName(userName);
    }



}
